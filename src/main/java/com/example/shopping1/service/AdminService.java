package com.example.shopping1.service;

import com.example.shopping1.entities.Admin;
import com.example.shopping1.mapper.AdminMapper;
import com.example.shopping1.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    SellerMapper sellerMapper;

    public Admin login(String username, String password) {
        return adminMapper.findByUnameAndPsd(username,password);
    }

    public void deleteOne(Integer sid) {
        sellerMapper.delete(sid);
    }
}
