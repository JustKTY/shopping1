package com.example.shopping1.controller;

import com.example.shopping1.component.CheckCodeUtil;
import com.example.shopping1.entities.User;
import com.example.shopping1.mapper.UserMapper;
import com.example.shopping1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
     * 【用户注册模块】
     * 来到注册页面
     * @return
     */
    @GetMapping("/register")
    public String toRegister(){
        System.out.println("跳转到注册页面");
        return "register";
    }

    /**
     * 【用户注册模块】
     * 注册页面生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/checkCode")
    public void getCheckCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        CheckCodeUtil checkCodeUtil = new CheckCodeUtil();
        System.out.println("验证码！");
        checkCodeUtil.getRandcode(request,response);

    }

    /**
     * 【用户注册模块】
     * 注册功能具体实现，
     * 注册成功，重定向到主页面
     * 否则，回到注册页面
     * @return
     */
    @PostMapping("/register")
    public String register(User user, Map<String,Object> map,
                           HttpServletRequest request, HttpSession session){

        //1、验证校验
        String check = request.getParameter("check");
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); //为了保证验证码只能使用一次
        //1.1 比较
        if(checkcode_server!=null&&!checkcode_server.equalsIgnoreCase(check)){
            map.put("error_msg","验证码错误！");
            return "register";
        }
        //2、验证码正确，注册
        boolean flag = userService.addUser(user);
        if(flag){
            session.setAttribute("loginUser",user);
            return "redirect:/main.html";
        }else{
            map.put("error_msg","用户名已存在，注册失败！");
        }
        return "register";
    }


    /**
     * 【用户登录模块】
     * 来到登录页面
     */
    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    /**
     * 【用户登录模块】
     * 登录页面实现
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session){
        if(username.isEmpty()||password.isEmpty()){
            map.put("error_msg","用户名或密码不能为空！");
            return "login";
        }
        User user = userService.login(username,password);
        if(user!=null){
            session.setAttribute("loginUser",user);
            session.setAttribute("msg", false);
            return "redirect:/index.html";
        }else{
            map.put("error_msg","用户名或密码错误！");
            return "login";
        }


    }

    @GetMapping("/toPersonal")
    public String toPersonal(){
        return "personal";
    }
}
