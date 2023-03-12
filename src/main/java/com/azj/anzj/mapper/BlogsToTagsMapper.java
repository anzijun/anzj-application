package com.azj.anzj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogsToTagsMapper {

    int deleteRel(Integer blogId);
    int addRel(@Param("blogId") Integer blogId,@Param("tagIds") List<Integer> tagIds);

}
