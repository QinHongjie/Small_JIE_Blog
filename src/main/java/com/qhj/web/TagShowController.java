package com.qhj.web;

import com.qhj.pojo.Blog;
import com.qhj.pojo.Tag;
import com.qhj.service.BlogService;
import com.qhj.service.TagService;
import com.qhj.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by QHJ on 2021/3/28
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tagsByIdPage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                @PathVariable Long id, Model model) {
        init(id, pageable, model);
        return "tags";
    }

    @GetMapping("/tags")
    public String tagsPage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        init(-1l, pageable, model);
        return "tags";
    }

    private void init(Long id, Pageable pageable, Model model){
        List<Tag> tags = tagService.listTagTop(100);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        Page<Blog> pageBlogs = blogService.listBlog(pageable, id);
        for (Blog b : pageBlogs) {
            b.init();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", pageBlogs);
        model.addAttribute("activeTagId", id);
    }

}
