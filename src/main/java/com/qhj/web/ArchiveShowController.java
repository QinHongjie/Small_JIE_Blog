package com.qhj.web;

import com.qhj.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by QHJ on 2021/3/28
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    /**
     * 归档页
     */
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archivesBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }

}
