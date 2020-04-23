package com.avenger.jt808.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public interface SettingParams<T> {

    default Class getRealType() {
        // 获取当前new的对象的泛型的父类类型
        Type[] pt = this.getClass().getGenericInterfaces();
        // 获取第一个类型参数的真实类型
        return (Class<T>) ((ParameterizedType) pt[0]).getActualTypeArguments()[0];
    }

    int getId();

    T getParam();
}
