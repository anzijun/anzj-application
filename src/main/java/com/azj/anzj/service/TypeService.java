package com.azj.anzj.service;

import com.azj.anzj.pojo.Type;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface TypeService {
    //添加一个分类
    int addType(Type type);
    //根据id删除一个分类
    int deleteType(int id);
    //根据id更新一个分类
    int updateType(Type type);
    //根据ID获取一个分类
    Type getTypeById(int id);
    //根据名字查询分类
    Type getTypeByName(String name);
    //获取所有分类进行分类
    PageInfo<Type> queryTypePage(Integer pageNum,Integer pageSize);
    //获取所有分类
    List<Type> listType();
    //获取前6个博客最多分类
    List<Type> listTopType(Integer limit);
}
