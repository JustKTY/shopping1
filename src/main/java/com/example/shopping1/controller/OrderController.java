package com.example.shopping1.controller;

import com.example.shopping1.entities.Order;
import com.example.shopping1.entities.Product;
import com.example.shopping1.entities.User;
import com.example.shopping1.service.OrderService;
import com.example.shopping1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @GetMapping("/toShoppingCar")
    public String toShoppingCar(HttpSession session, Map<String,Object> map, Model model){
        User user =(User) session.getAttribute("loginUser");
//        if(user==null){
//            map.put("error_msg","你尚未登录，请登录");
//            return "forward:/index.html";
//        }
        if(user!=null){
            List<Order> list = orderService.queryByUid(user.getUid());
            double ordersPrice = 0;int ordersNum=0;
            for(Order o :list){
                ordersPrice+=o.getTotalprice();
                ordersNum++;
            }
            model.addAttribute("orders", list);
            model.addAttribute("ordersNum",ordersNum);
            model.addAttribute("ordersPrice",ordersPrice);
            return "shoppingcar";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/addToCar/{pid}")
    public String addToCar(@PathVariable("pid") Integer pid, HttpSession session, Model model, Map<String,Object> map){
        User user =(User) session.getAttribute("loginUser");
//        if(user==null){
//            map.put("error_msg","你尚未登录，请登录");
//            return "redirect:/goodsDetail/"+pid;
//        }
        if(user!=null){
            Product product = productService.queryOne(pid);
            Order order = new Order();
            order.setOnum(1);
            order.setTotalprice(product.getPrice());
            order.setStatus("N");
            order.setUid(user.getUid());
            order.setProduct(product);
            System.out.println(product);
            Boolean flag = orderService.addToCar(order);
//            session.setAttribute("msg", false);
            if(!flag){
                model.addAttribute("error_msg","你已收藏该商品");
                return "redirect:/goodsDetail/"+pid;
            }
        }
        return "redirect:/order/toShoppingCar";
    }

    @GetMapping("/deleteSelected")
    public String deleteSelected(HttpServletRequest request){

        System.out.println("确实到了删除方法");
        String[] oids = request.getParameterValues("oid");
        for(String o:oids){
            int oid = Integer.parseInt(o);
            orderService.deleteByOid(oid);
        }

        return "redirect:/order/toShoppingCar";
    }

    @GetMapping("/alterNum/{oid}")
    public String alterNum(@RequestParam("flag") String flag,@PathVariable("oid")String o){
        int oid = Integer.parseInt(o);
        System.out.println(oid);
        Order order = orderService.queryByOid(oid);
        System.out.println(order);
        int newNum;
        if(flag.equals("1")){
            newNum = order.getOnum()+1;
        }else{
            newNum = order.getOnum()-1;
        }
        if(newNum<0){
            newNum = 0;
        }
        orderService.alterNum(oid,newNum);
        return "redirect:/order/toShoppingCar";
    }
}
