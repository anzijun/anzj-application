package com.azj.anzj.web;

import com.azj.anzj.pojo.Comment;
import com.azj.anzj.pojo.User;
import com.azj.anzj.service.BlogService;
import com.azj.anzj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model){

       model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session){
        Integer blogId = comment.getBlogId();
        comment.setBlog(blogService.getBlogById(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }

        commentService.addComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
