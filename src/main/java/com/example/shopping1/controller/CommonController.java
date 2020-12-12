package com.example.shopping1.controller;

import com.example.shopping1.entities.Product;
import com.example.shopping1.entities.Seller;
import com.example.shopping1.service.ProductService;
import com.example.shopping1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommonController {

    @Autowired
    ProductService productService;
    @Autowired
    SellerService sellerService;

    /**
     * 【首页展示】
     */
    @GetMapping({"/","/main.html","index.html"})
    public String index(Model model){
        List<Product> products = productService.queryAll();
        model.addAttribute("products",products);
        int rows = (products.size()%5==0)?(products.size()/5):(products.size()/5+1);
        model.addAttribute("size",rows);
        return "index";
    }

    /**
     * 【来到商品详情页面】
     */
    @GetMapping("/goodsDetail/{pid}")
    public String goodDetail(@PathVariable("pid") Integer pid,Model model){

        Product product = productService.queryOne(pid);
        Seller seller = sellerService.findOne(product.getSid());
        model.addAttribute("seller",seller);
        model.addAttribute("product",product);
        return "goodDetails";
    }

    /**
     * 查询功能
     */
    @GetMapping("/search")
    public String search(Model model, @RequestParam("input") String name){


        List<Product> products = productService.search(name);
        model.addAttribute("products",products);
        int rows = (products.size()%5==0)?(products.size()/5):(products.size()/5+1);
        model.addAttribute("size",rows);
        return "index";
    }

}
