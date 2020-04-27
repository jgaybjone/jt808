package com.avenger.jt808.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by jg.wang on 2019/10/26.
 * Description:
 */
public interface BaseService<T extends Serializable, I extends Serializable, J extends JpaRepository<T, I>> {


    /**
     * get jpa repository
     *
     * @return jpaRepository
     */
    J getRepository();

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param consumer 消费函数
     */
    @Transactional
    default void crudAndConsumer(Consumer<J> consumer) {
        consumer.accept(getRepository());
    }

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param function 应用函数
     */
    @Transactional
    default <E> E crudAndFunction(Function<J, E> function) {
        return function.apply(getRepository());
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default Optional<T> findById(I id) {
        return getRepository().findById(id);
    }

    default <S extends T> Optional<S> findOne(Example<S> example) {
        return getRepository().findOne(example);
    }

    default List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    @Transactional
    default <S extends T> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }

    default <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return getRepository().findAll(example, pageable);
    }

    default Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Transactional
    default <S extends T> S saveEntity(S entity) {
        return getRepository().save(entity);
    }

}
