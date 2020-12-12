package com.example.shopping1.controller;

import com.example.shopping1.entities.Admin;
import com.example.shopping1.entities.Seller;
import com.example.shopping1.entities.User;
import com.example.shopping1.service.AdminService;
import com.example.shopping1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    SellerService sellerService;
    /**
     * 【管理员登录】
     */
    @PostMapping("/login")
    public String login(@RequestParam("aname") String username,
                        @RequestParam("apassword") String password,
                        Map<String, Object> map, HttpSession session) {
        if (username.isEmpty() || password.isEmpty()) {
            map.put("error_msg", "用户名或密码不能为空！");
            return "login";
        }
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("loginAdmin", admin);
            List<Seller> list = sellerService.queryAll();
            map.put("sellers",list);
            return "manager";
        } else {
            map.put("error_msg", "用户名或密码错误！");
            return "login";
        }
    }

    /**
     * 【】
     */
    @GetMapping("/delSeller/{sid}")
    public String delete(@PathVariable("sid") Integer sid,Map<String,Object> map){
        adminService.deleteOne(sid);
        List<Seller> list = sellerService.queryAll();
        map.put("sellers",list);
        return "manager";

    }

}
