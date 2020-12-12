package com.example.shopping1.controller;

import com.example.shopping1.component.CheckCodeUtil;
import com.example.shopping1.entities.Product;
import com.example.shopping1.entities.Seller;
import com.example.shopping1.entities.User;
import com.example.shopping1.service.ProductService;
import com.example.shopping1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/seller")
@Controller
public class SellerController {
    @Autowired
    SellerService sellerService;
    @Autowired
    ProductService productService;


    /**
     * 【商家注册模块】
     */

    /*
     * 来到商家注册页面
     */
    @GetMapping("/register")
    public String toRegister(){
        return "sellerRegister";
    }

    /*
     * 注册页面生成验证码
     */
    @RequestMapping("/checkCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        CheckCodeUtil checkCodeUtil = new CheckCodeUtil();
        System.out.println("验证码！");
        checkCodeUtil.getRandcode(request,response);

    }

    /*
     * 注册功能具体实现，
     * 注册成功，重定向到卖家中心
     * 否则，回到注册页面
     */
    @PostMapping("/register")
    public String register(Seller seller, Map<String,Object> map,
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
        boolean flag = sellerService.addSeller(seller);
        if(flag){
            //注册成功，重新获取完整seller对象放在会话域中[主要缺少sid]
            Seller seller1 = sellerService.login(seller.getSname(), seller.getSpassword());
            session.setAttribute("loginSeller",seller1);
            return "redirect:/seller/toSeller";
        }else{
            map.put("error_msg","商家名已存在，注册失败！");
        }
        return "register";
    }






    /**
     * 【商家登录操作模块】
     */

    /*
     * 商家登录
     */
    @PostMapping("/login")
    public String login(@RequestParam("sname")String sname,
                        @RequestParam("spassword") String password,
                        Map<String, Object>map, HttpSession session){

        if(sname.isEmpty()||password.isEmpty()){
            map.put("error_msg","用户名或密码不能为空！");
            return "login";
        }
        Seller seller = sellerService.login(sname,password);
        if(seller!=null){

            //查询商家的商品
            List<Product> products = productService.queryAllProducts(seller.getSid());
            seller.setProducts(products);

            session.setAttribute("loginSeller",seller);
            return "redirect:/seller/toSeller";

        }else{
            map.put("error_msg","用户名或密码错误！");
            return "login";
        }
    }

    /*
     *来到商家页面
     */
    @GetMapping("/toSeller")
    public String toSeller(){
        return "seller";
    }


    /*
     * 上传商品
     */
    @PostMapping(value = "/uploadProduct",produces = "multipart/form-data;charset=UTF-8")
    public String uploadProduct(@RequestParam("fileName")MultipartFile file, Product product,
                                Map<String,Object> map,HttpSession session)
    {
        if(session.getAttribute("loginSeller")==null){
            map.put("error_msg","商家请先登录！");
            return "seller";
        }
        String url="";
        System.out.println(product);
        System.out.println("上传商品!");
        //判断文件是否空
        if(file.isEmpty()){
            map.put("error_msg","上传图片不可为空");
            return "seller";
        }
        //获取文件
        String filename = file.getOriginalFilename();
        System.out.println("上传文件名为:"+filename+"\n");
        filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+filename;
        System.out.println("");
        String path = "D:/images/"+filename;
        System.out.println("文件绝对路径："+path);
        //创建文件路径
        File dest = new File(path);
        //判断文件是否已经存在
        if(dest.exists()){
            map.put("error_msg","上传文件已存在");
            return "seller";
        }
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try{
            //上传文件
            file.transferTo(dest);
            System.out.println("保存文件路径:"+path+"\n");
            url="http://10.101.40.153:8080/images/shop/"+filename; //运行项目
            System.out.println(url);

            product.setSales(0);
            product.setPImgUrl(url);

            productService.uploadProduct(product);

        } catch (IOException e) {
            map.put("error_msg","上传失败");
            return "seller";
        }
        System.out.println( "上传成功！文件url=="+url);

        //重新更新商家商品展示
        Seller seller = sellerService.findOne(product.getSid());
        List<Product> products = productService.queryAllProducts(seller.getSid());
        seller.setProducts(products);
        session.setAttribute("loginSeller",seller);
        return "redirect:/seller/toSeller";
    }

    /*
     *退出功能【用户和商家】
     */
    @GetMapping("/exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/main.html";
    }

    @GetMapping("/delProduct/{pid}")
    public String deletePro(@PathVariable("pid") Integer pid,HttpSession session){
        //删除商品
        productService.deletPro(pid);

        Seller seller = (Seller) session.getAttribute("loginSeller");
        //查询商家的商品
        List<Product> products = productService.queryAllProducts(seller.getSid());
        seller.setProducts(products);

        session.setAttribute("loginSeller",seller);
        return "redirect:/seller/toSeller";
    }


}
