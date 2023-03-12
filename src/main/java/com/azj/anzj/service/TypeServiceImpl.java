package com.azj.anzj.service;

import com.azj.anzj.mapper.TypeMapper;
import com.azj.anzj.pojo.Type;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;


    @Transactional
    @Override
    public int addType(Type type) {
        return typeMapper.addType(type);
    }
    @Override
    public Type getTypeById(int id) { return typeMapper.getTypeById(id); }

    @Override
    public PageInfo<Type> queryTypePage(Integer pageNum, Integer pageSize) {
        //使用PageHelper实现分页
        PageHelper.startPage(pageNum,pageSize);
        List<Type> types = typeMapper.queryAllType();
        PageInfo<Type> typePageInfo = new PageInfo<>(types,pageSize);
        return typePageInfo;
    }

    @Override
    public List<Type> listTopType(Integer limit){
        return typeMapper.listTopType(limit);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeMapper.queryAllType();
    }
    @Transactional
    @Override
    public int deleteType(int id) {
        return typeMapper.deleteType(id);
    }
    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }
}
