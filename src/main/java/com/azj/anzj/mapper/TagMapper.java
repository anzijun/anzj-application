package com.azj.anzj.mapper;

import com.azj.anzj.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    //添加标签
    int addTag(Tag tag);
    //根据删除标签
    int deleteTag(int id);
    //更新标签
    int updateTag(Tag tag);
    //根据ID查询标签
    Tag getTagById(int id);
    //根据标签名称查询标签
    Tag getTagByName(String name);
    //查询所有标签
    List<Tag> queryAllTag();
    //根据id集合查询标签
    List<Tag> listTag(List<Integer> ids);
    //首页查询博客最多前十个标签
    List<Tag> listTopTag(@Param("limit") Integer limit);
}
