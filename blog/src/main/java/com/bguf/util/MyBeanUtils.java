package com.bguf.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;


public class MyBeanUtils
{
    /***
     * 将源数据中所有为空的属性名，放到一个字符串数组中
     */
    public static String[] getNullPropertyNames(Object source)
    {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds)
        {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null)
            {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
