package com.azj.anzj.service;

import com.azj.anzj.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Integer blogId);
    int addComment(Comment comment);
}
