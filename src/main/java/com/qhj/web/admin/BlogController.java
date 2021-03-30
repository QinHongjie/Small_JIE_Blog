package com.qhj.web.admin;

import com.alibaba.fastjson.JSON;
import com.qhj.pojo.Blog;
import com.qhj.pojo.User;
import com.qhj.service.BlogService;
import com.qhj.service.TagService;
import com.qhj.service.TypeService;
import com.qhj.utils.code.Result;
import com.qhj.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by QHJ on 2021/3/25
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 后台博客列表页
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return "admin/blogs";
    }

    /**
     * 博客查询列表
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /**
     * 后台博客发布页
     */
    @GetMapping("/blog/input")
    public String inputPage(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "admin/blog_input";
    }

    /* 初始化设置类型和便签 */
    private void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    /**
     * 博客编辑页
     */
    @GetMapping("/blog/edit/{id}")
    public String blogEdit(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blog_input";
    }

    /**
     * 博客发布 & 博客编辑
     */
    @PostMapping("/blog/input")
    public String input(Blog blog, RedirectAttributes attributes) {
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(context)) {
            UserDetails userDetails = (UserDetails) context;
            User user = (User)userDetails;
            blog.setUser(user);
        }
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId()==null){
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog, blog.getId());
        }

        if (b == null) {
            attributes.addFlashAttribute("message","操作失败！");
        } else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blog/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return "redirect:/admin/blogs";
    }

}
