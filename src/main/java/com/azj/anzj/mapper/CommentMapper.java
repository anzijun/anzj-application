package com.azj.anzj.mapper;

import com.azj.anzj.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
//根据id获取评论
    Comment getCommentById(Integer id);
   //根据博客id获取所有博客下的评论
    List<Comment> listCommentByBlogId(Integer blogId);
    //查询父级评论
    List<Comment> findByParentIdNull(@Param("blogId") Integer blogId);
    //查询一级回复
    List<Comment> findByParentIdNotNull(@Param("id") Integer id,@Param("blogId") Integer blogId);
    //查询二级以及所有子集回复
    List<Comment> findByReplayId(@Param("childId") Integer childId,@Param("blogId") Integer blogId);
//    添加一个评论
    int addComment(Comment comment);
}
