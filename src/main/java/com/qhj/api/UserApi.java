package com.qhj.api;

import com.alibaba.fastjson.JSON;
import com.qhj.pojo.User;
import com.qhj.utils.code.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QHJ on 2021/3/29
 */
@Controller
@RequestMapping("/api")
public class UserApi {

    @GetMapping("/getUserBySession")
    @ResponseBody
    public Result getUserBySession(){
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!"anonymousUser".equals(context)) {
            UserDetails userDetails = (UserDetails) context;
            User user = (User)userDetails;
            user.setBlogs(null);
            user.setPassword(null);
            return Result.success(JSON.toJSON(user));
        }
        return null;
    }







//    @Autowired
//    private TypeService typeService;
//
//    @RequestMapping("/getTypeList")
//    @ResponseBody
//    public Result getTypeList(){
//        List<Type> typeList = typeService.listType();
//        List<Map<String,String>> listMap = new ArrayList<>();
//        for (int i = 0; i < typeList.size(); i++) {
//            Type type = typeList.get(i);
//            Map<String, String> map = new HashMap<>();
//            map.put("id", type.getId()+"");
//            map.put("name", type.getName());
//            map.put("blogsCount", type.getBlogs().size()+"");
//            listMap.add(map);
//        }
//        return Result.success(listMap);
//    }
}