package com.azj.anzj.mapper;

import com.azj.anzj.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {
    //添加分类
    int addType(Type type);
    //删除一个分类
    int deleteType(int id);
    //更新一个分类
    int updateType(Type type);
    //查询分类
    Type getTypeById(int id);
    //根据名称查询分类
    Type getTypeByName(String name);
    //查询所有分类
    List<Type> queryAllType();

    List<Type> listTopType(@Param("limit") Integer limit);
}
