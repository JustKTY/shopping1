package com.example.shopping1.entities;

import lombok.Data;

@Data
public class Product {
    private int pid;
    private String pname;
    private double price;
    private String productIntroduce;
    private int stock; //库存
    private int sales; //销量
    private int sid;//商家id
    private String pImgUrl; //商品图片路径

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductIntroduce() {
        return productIntroduce;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPImgUrl() {
        return pImgUrl;
    }

    public void setPImgUrl(String pImgUrl) {
        this.pImgUrl = pImgUrl;
    }
}
