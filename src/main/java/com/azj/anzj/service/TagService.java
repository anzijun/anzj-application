package com.azj.anzj.service;

import com.azj.anzj.pojo.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TagService {

    //添加一个标签
    int addTag(Tag tag);
    //根据id删除一个标签
    int deleteTag(Integer id);
    //更新一个标签
    int updateTag(Tag tag);
    //根据id查询一个标签
    Tag getTagById(Integer id);
    //根据标签名称查询一个标签
    Tag getTagByName(String name);
    //使用分页插件对查到的所有标签进行分页处理
    PageInfo<Tag> queryTagPage(Integer pageNum,Integer pageSize);
    //查询所有标签
    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTopTag(Integer limit);
}
