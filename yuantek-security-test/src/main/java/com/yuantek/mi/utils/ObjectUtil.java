package com.yuantek.mi.utils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ObjectUtil {

    public static void pretty(Object object) {
        System.out.println(ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE));
    }
}
