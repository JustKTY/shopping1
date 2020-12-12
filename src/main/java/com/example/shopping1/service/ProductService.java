package com.example.shopping1.service;

import com.example.shopping1.entities.Product;
import com.example.shopping1.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;

    public void uploadProduct(Product product) {
        productMapper.addProduct(product);
    }
    /*
     *查询某一商家的全部商品
     */
    public List<Product> queryAllProducts(int sid) {
        return productMapper.queryAll(sid);
    }

    public void deletPro(Integer id) {
        int pid = id.intValue();
        productMapper.delete(pid);
    }

    //查询所有商品
    public List<Product> queryAll() {
        return productMapper.queryAllPro();
    }

    public Product queryOne(Integer pid) {
        return productMapper.queryOne(pid);
    }

    public List<Product> search(String name) {
        return productMapper.queryByName(name);
    }
}
