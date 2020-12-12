package com.example.shopping1.entities;

import lombok.Data;

import java.util.List;

@Data
public class Seller {
    private int sid;
    private String sname;
    private String conphone;
    private String address;
    private int ranking;
    private String spassword;
    private List<Product> products;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getConphone() {
        return conphone;
    }

    public void setConphone(String conphone) {
        this.conphone = conphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

