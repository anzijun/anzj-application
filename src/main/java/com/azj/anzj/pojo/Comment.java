package com.azj.anzj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

        private Integer id;
        private String nickname;
        private String email;
        private String content;
        private String avatar;
        private Date createTime;
        private Integer blogId;
        private Integer parentCommentId;
        private boolean adminComment;

        private Blog blog;
        private String parentNickname;
        private List<Comment> replyComments;
        private Comment parentComment;

}
