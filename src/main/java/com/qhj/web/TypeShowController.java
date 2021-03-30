package com.qhj.web;

import com.qhj.pojo.Blog;
import com.qhj.pojo.Type;
import com.qhj.service.BlogService;
import com.qhj.service.TypeService;
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
 * 分类显示控制器
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String typesByIdPage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        init(id, pageable, model);
        return "types";
    }

    @GetMapping("/types")
    public String typesPage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        init(-1l, pageable, model);
        return "types";
    }

    private void init(Long id, Pageable pageable, Model model){
        List<Type> types = typeService.listTypeTop(100);
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> pageBlogs = blogService.listBlog(pageable, blogQuery);
        for (Blog b : pageBlogs) {
            b.init();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", pageBlogs);
        model.addAttribute("activeTypeId", id);
    }

}
