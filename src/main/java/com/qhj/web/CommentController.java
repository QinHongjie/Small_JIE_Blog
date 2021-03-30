package com.qhj.web;

import com.qhj.pojo.Comment;
import com.qhj.pojo.User;
import com.qhj.service.BlogService;
import com.qhj.service.CommentService;
import com.qhj.utils.code.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QHJ on 2021/3/27
 * 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = null;
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(context)) {
            UserDetails userDetails = (UserDetails) context;
            user = (User)userDetails;
        }
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(commentService.getAvatarPath());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }


}
