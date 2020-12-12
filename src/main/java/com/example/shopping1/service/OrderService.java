package com.example.shopping1.service;

import com.example.shopping1.entities.Order;
import com.example.shopping1.entities.Product;
import com.example.shopping1.mapper.OrderMapper;
import com.example.shopping1.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;

    public  List<Order> queryByUid(int uid) {
        return orderMapper.queryByUid(uid);
    }

    public Boolean addToCar(Order order) {

        Order o = orderMapper.queryOne(order.getUid(),order.getProduct().getPid());
        if(o!=null){
            return false;
        }
        orderMapper.addOne(order);
        return true;
    }


    public void deleteByOid(int oid) {
        orderMapper.deleteByOid(oid);
    }

    public Order queryByOid(int oid) {
        return orderMapper.queryByOid(oid);
    }

    public void alterNum(int oid, int newNum) {
        orderMapper.alterNum(oid,newNum);
        //改变总价格
        Order order = orderMapper.queryByOid(oid);
        Product product = order.getProduct();
        Double totalprice = product.getPrice()* newNum;
        orderMapper.alterTotalPrice(totalprice,oid);
    }
}
