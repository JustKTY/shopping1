package com.example.shopping1.service;

import com.example.shopping1.entities.Product;
import com.example.shopping1.entities.Seller;
import com.example.shopping1.entities.User;
import com.example.shopping1.mapper.ProductMapper;
import com.example.shopping1.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerMapper sellerMapper;


    public Seller login(String name, String password){
        return sellerMapper.findSellerByNameAndPassword(name,password);
    }


    public Seller findOne(int sid) {
        return sellerMapper.findSellerBySid(sid);
    }



    public boolean addSeller(Seller seller) {
        // 1、根据用户名查询用户对象
        Seller u = sellerMapper.findByUsername(seller.getSname());
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }

        //初始商家评分90分
        seller.setRanking(90);

        // 2、保存用户信息
        sellerMapper.addSeller(seller);
        return true;
    }


    public List<Seller> queryAll() {
        return sellerMapper.queryAll();
    }
}
