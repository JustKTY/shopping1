package com.example.shopping1.mapper;

import com.example.shopping1.entities.Seller;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SellerMapper {



    @Select("select * from seller where sname = #{name} and spassword = #{password}")
    public Seller findSellerByNameAndPassword(String name, String password);

    @Select("select * from seller where sid = #{sid}")
    Seller findSellerBySid(int sid);

    @Select("select * from seller where sname = #{sname}")
    Seller findByUsername(String sname);

    @Insert("insert into seller(sname,conphone,address,ranking,spassword) " +
            "values(#{sname},#{conphone},#{address},#{ranking},#{spassword})")
    void addSeller(Seller seller);

    @Select("select * from seller")
    List<Seller> queryAll();

    @Delete("delete from seller where sid = #{sid}")
    void delete(Integer sid);
}
