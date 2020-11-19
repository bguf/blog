package com.bguf.service.impl;

import com.bguf.dao.TypeRepository;
import com.bguf.entity.Type;
import com.bguf.handler.MyNotFindException;
import com.bguf.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 4:26 下午 2020/10/5
 */
@Component
public class TypeServiceImpl implements TypeService
{
    @Autowired
    private TypeRepository repository;

    /***
     * 保存type
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Type saveType(Type type)
    {
        return repository.save(type);
    }

    /***
     * 根据类型id查询type
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Type getType(Long id)
    {
        return repository.findById(id).get();
    }

    /***
     * 根据类型name查询type
     * @param name
     * @return
     */
    @Transactional
    @Override
    public Type getTypeByName(String name)
    {
        return repository.findByName(name);
    }

    /***
     * 分页查询所有type，type有name为空的，筛选掉
     * @param pageable
     * @return
     */
    @Override
    @Transactional
    public Page<Type> typelist(Pageable pageable)
    {
        return repository.findAll(new Specification<Type>()
        {
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                Predicate predicate = builder.notEqual(root.<String>get("name"), "");
                query.where(predicate);
                return null;
            }
        }, pageable);
    }

    /***
     * 查询出所有的type
     * @return
     */
    @Override
    @Transactional
    public List<Type> listTypes()
    {
        List<Type> types = repository.findAll();
        List<Type> list = new ArrayList<>();
        for (Type type : types)
        {
            if (!type.getName().equals(""))
            {
                list.add(type);
            }
        }
        return list;
    }

    /***
     * 根据type拥有的博客数量降序查询出所有的type
     * @param size
     * @return
     */
    @Override
    @Transactional
    public List<Type> listTypes(Integer size)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findType(pageable);
    }

    /***
     * 更新type
     * @param id
     * @param name
     * @return
     */
    @Transactional
    @Override
    public Type updateType(Long id, String name)
    {
        Type type1 = repository.findById(id).get();
        if (type1 == null)
        {
            throw new MyNotFindException("找不到此类型");
        }
        repository.update(id, name);
        return repository.findById(id).get();
    }

    /***
     * 删除类型
     * @param id
     */
    @Transactional
    @Override
    public void deleteType(Long id)
    {
        repository.deleteById(id);
    }
}
