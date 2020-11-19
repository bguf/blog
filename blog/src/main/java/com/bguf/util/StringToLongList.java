package com.bguf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 7:14 下午 2020/10/6
 */
public class StringToLongList
{
    public static List<Long> toList(String strs)
    {
        List<Long> list = new ArrayList<>();
        if (!"".equals(strs) && strs != null)
        {
            String[] strings = strs.split(",");
            for (int i = 0; i < strings.length; i++)
            {
                list.add(new Long(strings[i]));
            }
        }
        return list;
    }
}
