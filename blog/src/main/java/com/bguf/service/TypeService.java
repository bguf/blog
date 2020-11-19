package com.bguf.service;

import com.bguf.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date 4:22 下午 2020/10/5
 */
@Service
public interface TypeService
{
    //增
    Type saveType(Type type);
    //查
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> listTypes();
    List<Type> listTypes(Integer size);
    //分页
    Page<Type> typelist(Pageable pageable);
    //改
    Type updateType(Long id, String typename);
    //删
    void deleteType(Long id);
}
