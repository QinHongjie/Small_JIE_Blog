package com.qhj.service.impl;

import com.qhj.dao.CommentRepository;
import com.qhj.pojo.Comment;
import com.qhj.service.CommentService;
import com.qhj.utils.IpAddressUtil;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QHJ on 2021/3/27
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transient
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 从用户ip4地址中取尾数进行处理 实现个用户随机分配头像[1,2,3]
     * @return
     */
    @Override
    public String getAvatarPath() {
        // 设置头像
        String ip = IpAddressUtil.getIp4();
        if (ip==null || "".equals(ip)) {
            return null;
        } else {
            int imgIndent = Integer.parseInt(ip.substring(ip.length() - 1));
            if (imgIndent == 0) {
                imgIndent += 1;
            } else if (imgIndent == 2) {
                imgIndent -= 1;
            } else {
                while (imgIndent > 3) {
                    imgIndent /= 2;
                }
            }
            return "/img/avatar0"+ imgIndent +".jpg";
        }
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment (List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for ( Comment comment : comments ) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView. add(c) ;
        }
        //合并评论的各层子代到第级子代集合中
        combineChildren(commentsView) ;
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments ) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively( reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments( tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    // 存放迭代找出所有子代的集合[临时容器]
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment) {
        //顶节点添加到临时存放集合
        tempReplys.add( comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }


}
