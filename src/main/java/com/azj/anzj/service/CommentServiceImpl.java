package com.azj.anzj.service;

import com.azj.anzj.mapper.CommentMapper;
import com.azj.anzj.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    private List<Comment> tempReplays = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Integer blogId) {
        List<Comment> comments = commentMapper.findByParentIdNull(blogId);
        for(Comment comment : comments){
            Integer id = comment.getId();
            String parentNickname1 = comment.getNickname();
            List<Comment> childComments = commentMapper.findByParentIdNotNull(id,blogId);
            //查询出子评论
            combineChildren(childComments,parentNickname1,blogId);
            comment.setReplyComments(tempReplays);
            tempReplays = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(List<Comment> childComments,String parentNickname1,Integer blogId){
        //判断当前评论下是否有子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplays.add(childComment);
                Integer childId = childComment.getId();
                //查询二级以所有子集评论
                recursively(childId,parentNickname,blogId);
            }

        }
    }

//    递归获取子评论集合，直到没有子评论
    private void recursively(Integer childId,String parentNickname1,Integer blogId){
        List<Comment> replayComments = commentMapper.findByReplayId(childId,blogId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Integer replayId = replayComment.getId();
                tempReplays.add(replayComment);
                recursively(replayId,parentNickname,blogId);
            }
        }
    }

    @Transactional
    @Override
    public int addComment(Comment comment) {
        Integer parentCommentId = comment.getParentCommentId();
        if(parentCommentId != -1){
            comment.setParentComment(commentMapper.getCommentById(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.addComment(comment);
    }
}
