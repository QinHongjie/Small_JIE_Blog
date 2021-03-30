package com.qhj.web.admin;

import com.qhj.pojo.User;
import com.qhj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by QHJ on 2021/3/25
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 后台欢迎页
     */
    @GetMapping({"/index",""})
    public String indexPage(){
        return "admin/index";
    }

    /**
     * 后台登录页
     */
    @GetMapping("/login")
    public String loginPage(HttpSession session){
        if (session.getAttribute("user") != null){
            return "admin/index";
        }
        return "admin/login";
    }


    /**
     * 后台登录请求
     */
//    @PostMapping("/login")
//    public String adminLogin(@RequestParam String username,
//                             @RequestParam String password,
//                             HttpSession session, RedirectAttributes attributes){
//        User user = userService.checkAdmin(username, password);
//        if (user != null) {
//            user.setPassword(null);
//            session.setAttribute("user",user);
//            return "redirect:/admin/index";
//        } else {
//            attributes.addFlashAttribute("message", "用户名和密码错误！");
//            return "redirect:/admin";
//        }
//        return "redirect:/admin";
//    }

    /**
     * 注销
     */
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        session.removeAttribute("user");
//        return "redirect:/admin";
//    }

}
