package com.avenger.jt808.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDocumentationScanner;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Created by jone.wang on 2018/11/26.
 * Description:
 */
@Configuration
@Profile({"dev", "test"})
@EnableSwagger2WebFlux
public class SwaggerWebFluxConfig {

    @Bean
    @ConditionalOnMissingBean
    DocumentationPluginsBootstrapper documentationPluginsBootstrapper(DocumentationPluginsManager documentationPluginsManager,
                                                                      List<RequestHandlerProvider> handlerProviders,
                                                                      DocumentationCache scanned,
                                                                      ApiDocumentationScanner resourceListing,
                                                                      TypeResolver typeResolver,
                                                                      Defaults defaults,
                                                                      PathProvider pathProvider,
                                                                      Environment environment) {
        return new DocumentationPluginsBootstrapper(documentationPluginsManager, handlerProviders, scanned, resourceListing, typeResolver, defaults, pathProvider, environment);
    }

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .groupName("baidu.com")
                .select().apis(RequestHandlerSelectors.basePackage("com.avenger.jt808.controller"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .globalOperationParameters(getGlobalParameters())
                .apiInfo(baseAPIInfo())
                .directModelSubstitute(Timestamp.class, Long.class);

    }

    private List<Parameter> getGlobalParameters() {
        final Parameter token2 = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("访问令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return Lists.newArrayList(token2);
    }

    private ApiInfo baseAPIInfo() {
        return new ApiInfo(
                "车联网核心服务",
                "jt808 terminal managerment",
                "1.0",
                "Terms of service",
                new Contact("Jone.wang", "https://www.baidu.com", "82099359@qq.com"),
                "License of API",
                "https://www.baidu.com", Lists.newArrayList(new StringVendorExtension("author", "jone"),
                new StringVendorExtension("author", "swagger2")));
    }

    @Aspect
    @Component
    public static class ReactiveServiceModelToSwagger2MapperAop {
        @Pointcut("execution(* springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl.mapDocumentation(..))")
        public void p() {
        }

        @Around("p()")
        public Object around(ProceedingJoinPoint pdj) throws Throwable {
            final Object proceed = pdj.proceed();
            if (proceed instanceof Swagger) {
                this.fixRef((Swagger) proceed);
            }
            return proceed;
        }

        @SuppressWarnings("deprecation")
        private Swagger fixRef(Swagger swagger) {
            swagger.getPaths().forEach((k, v) -> {
                final Operation get = v.getGet();
                if (Objects.nonNull(get)) {
                    get.getResponses().forEach((k2, v2) -> this.fixSchema(v2.getSchema()));
                }
                final Operation post = v.getPost();
                if (Objects.nonNull(post)) {
                    post.getResponses().forEach((k2, v2) -> this.fixSchema(v2.getSchema()));
                }

                final Operation put = v.getPut();
                if (Objects.nonNull(put))
                    put.getResponses().forEach((k2, v2) -> this.fixSchema(v2.getSchema()));

                final Operation patch = v.getPatch();
                if (Objects.nonNull(patch))
                    patch.getResponses().forEach((k2, v2) -> this.fixSchema(v2.getSchema()));
            });

            return swagger;
        }

        private void fixSchema(Property schema) {
            if (schema instanceof RefProperty) {
                final RefProperty refProperty = (RefProperty) schema;
                if (refProperty.getSimpleRef().startsWith("Mono«")) {
                    if (refProperty.getSimpleRef().equalsIgnoreCase("Mono«object»")) {
                        return;
                    }
                    final String replaced = refProperty.getSimpleRef()
                            .replaceFirst("Mono«", "")
                            .replaceAll("»$", "");
                    refProperty.set$ref("#/definitions/" + replaced);
                } else if (refProperty.getSimpleRef().startsWith("Flux«")) {
                    if (refProperty.getSimpleRef().equalsIgnoreCase("Flux«object»")) {
                        return;
                    }
                    final String replaced = refProperty.getSimpleRef()
                            .replaceFirst("Flux«", "")
                            .replaceAll("»$", "");
                    refProperty.set$ref("#/definitions/" + replaced);
                } else {
                    refProperty.set$ref(refProperty.get$ref()
                            .replace("definitions/MonoOf", "definitions/")
                            .replace("definitions/FluxOf", "definitions/"));
                }
            }
        }
    }
}
