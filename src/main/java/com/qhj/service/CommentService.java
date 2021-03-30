package com.qhj.service;

import com.qhj.pojo.Comment;

import java.util.List;

/**
 * Created by QHJ on 2021/3/27
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);

    String getAvatarPath();

}
