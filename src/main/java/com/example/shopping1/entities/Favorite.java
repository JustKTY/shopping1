package com.example.shopping1.entities;

import lombok.Data;

/**
 * 收藏类
 */
@Data
public class Favorite {
    private Product product; //收藏商品
    private User user;  //所属用户
    private String date; //收藏时间

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
