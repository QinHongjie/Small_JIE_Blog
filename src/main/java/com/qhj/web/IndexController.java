package com.qhj.web;

import com.qhj.pojo.Blog;
import com.qhj.service.BlogService;
import com.qhj.service.TagService;
import com.qhj.service.TypeService;
import com.qhj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * web页面控制器
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    /**
     * 首页
     */
    @GetMapping({"/","/index"})
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Blog> pageBlogs = blogService.listBlogCustom(pageable);
        for (Blog b : pageBlogs) {
            b.init();
        }
        model.addAttribute("page", pageBlogs);
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("typesCount", typeService.listType().size());
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("tagsCount", tagService.listTag().size());
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        model.addAttribute("admin_user", userService.getAdminUser());
        return "index";
    }

    /**
     * 搜索页面
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        Page<Blog> pageBlogs = blogService.listBlog(query, pageable);
        for (Blog b : pageBlogs) {
            b.init();
        }
        model.addAttribute("page", pageBlogs);
        model.addAttribute("query", query);
        return "search";
    }


    /**
     * 博客详情页
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndContent(id));
        return "blog";
    }

    /**
     * 关于我页
     */
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return  "_fragments :: newBlogList";
    }

}
