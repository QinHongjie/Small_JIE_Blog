package com.qhj.service;

import com.qhj.pojo.Blog;
import com.qhj.pojo.Tag;
import com.qhj.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by QHJ on 2021/3/26
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndContent(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    Page<Blog> listBlogCustom(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String, List<Blog>> archivesBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Blog blog, Long id);

    void deleteBlog(Long id);


}
