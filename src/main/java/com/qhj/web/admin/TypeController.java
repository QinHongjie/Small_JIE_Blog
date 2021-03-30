package com.qhj.web.admin;

import com.qhj.pojo.Type;
import com.qhj.service.TypeService;
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
 * Created by QHJ on 2021/3/25
 * 分类控制器
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String typesPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listPage(pageable));
        return "admin/types";
    }

    @GetMapping("/type/input")
    public String typeInputPage(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type_input";
    }

    @GetMapping("/type/{id}/edit")
    public String editInputPage(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type_input";
    }



    @PostMapping("/type/input")
    public String typeInput(@Validated Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/type_input";
        }
        Type t = typeService.saveTypes(type);
        if (t == null) {
            attributes.addFlashAttribute("message","添加失败！");
        } else {
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/type/input/{id}")
    public String typeEdit(@Validated Type type,BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/type_input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null) {
            attributes.addFlashAttribute("message","更新失败！");
        } else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/type/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/types";
    }

}
