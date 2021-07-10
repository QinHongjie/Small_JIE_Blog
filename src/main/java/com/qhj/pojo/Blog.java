package com.qhj.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QHJ on 2021/3/25
 * 博客文章实体类
 */
@Entity(name = "t_blog")
@Data
public class Blog {

    @Id
    @GeneratedValue
    private Long id; // 编号
    private String title; // 标题

    @Lob // LONGTEXT类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    private String content; // 内容

    private String firstPicture; // 首图
    private String flag; // 标记
    private Integer views; // 浏览次数
    private boolean appreciation; // 赞赏
    private boolean shareStatement; // 转载声明
    private boolean commentabled; // 评论
    private boolean published; // 是否发布
    private boolean recommend; // 推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime; // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime; // 更新时间

    @ManyToOne
    private Type type; // 分类
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>(); // 标签

    @ManyToOne
    private User user; // 用户

    @OneToMany(mappedBy = "blog", targetEntity = Comment.class, cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>(); // 评论

    @Transient
    private String tagIds; // 标签集
    @Transient
    private String description; // 前台展示博客描述


    public Blog() {
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
        if (!"".equals(content) && content!=null) {
            if( content.length() > 130) {
                this.description = content.substring(0, 130);
            } else {
                this.description = content;
            }
        }
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags){
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
