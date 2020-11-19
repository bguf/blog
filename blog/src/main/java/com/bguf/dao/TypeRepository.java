package com.bguf.dao;

import com.bguf.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类型的底层操作
 * @Author
 * @Description
 * @Date 4:27 下午 2020/10/5
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Long>
{
    //根据类型的名称查询类型
    Type findByName(String name);

    //查询所有的类型
    @Query("select t from Type t")
    List<Type> findType(Pageable pageable);

    //更新类型
    @Modifying
    @Query(value="update t_type set name=?2 where id=?1", nativeQuery=true)
    void update(Long id, String name);

    //根据条件查询所有的类型，分页
    Page<Type> findAll(Specification<Type> name, Pageable pageable);
}
