package com.example.shopping1.desktop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${cbs.imagesPath}")
    private String productImagePath;

    //视图映射
    @Bean  //将组件注册到容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            //实现其中的抽象方法
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                System.out.println("确实页面跳转了！");
//                registry.addViewController("/").setViewName("index");  //"/"代表本项目
                registry.addViewController("/detailsText").setViewName("detailsText");
                registry.addViewController("/post2").setViewName("post2");
                registry.addViewController("/calendar").setViewName("util/calendal");
            }
        };
        return adapter;
    }

    /**
     * 图片上传
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(productImagePath.equals("")||productImagePath.equals("${cbs.imagesPath}")){
            String imgPath = MyMvcConfig.class.getClassLoader().getResource("").getPath();
            System.out.println("1.上传配置类imgPath=="+imgPath+"\n");
            if(imgPath.indexOf(".jar")>0){
                imgPath = imgPath.substring(0,imgPath.indexOf(".jar"));
            }else if(imgPath.indexOf("classes")>0){
                imgPath = "file:"+imgPath.substring(0,imgPath.indexOf("classes"));
            }
            imgPath = imgPath.substring(0,imgPath.lastIndexOf("/"))+"/images/shop/";
            productImagePath = imgPath;
        }
        System.out.println("productImagePath====="+productImagePath+"\n");
        registry.addResourceHandler("/images/shop/**").addResourceLocations(productImagePath);
        System.out.println("2、上传图片productImagePath==="+productImagePath+"\n");
        super.addResourceHandlers(registry);
    }
}
