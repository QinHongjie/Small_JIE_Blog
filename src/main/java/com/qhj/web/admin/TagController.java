package com.qhj.web.admin;

import com.qhj.pojo.Tag;
import com.qhj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by QHJ on 2021/3/26
 * 标签控制器类
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagsPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page",tagService.listPage(pageable));
        return "admin/tags";
    }

    @GetMapping("/tag/input")
    public String tagInputPage(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag_input";
    }

    @GetMapping("/tag/{id}/edit")
    public String editInputPage(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag_input";
    }

    @PostMapping("/tag/input")
    public String typeInput(@Validated Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if (result.hasErrors()) {
            return "admin/tag_input";
        }
        Tag t = tagService.saveTags(tag);
        if (t == null) {
            attributes.addFlashAttribute("message","添加失败！");
        } else {
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tag/{id}/edit")
    public String tagEdit(@Validated Tag tag,BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if (result.hasErrors()) {
            return "admin/tag_input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null) {
            attributes.addFlashAttribute("message","更新失败！");
        } else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";
    }

}
