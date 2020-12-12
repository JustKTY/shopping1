package com.example.shopping1.mapper;

import com.example.shopping1.entities.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from order01 where uid=#{uid} and pid=#{pid}")
    @Results(id = "orderMap",value = {
            @Result(property = "oid",column = "oid"),
            @Result(property = "onum",column = "onum"),
            @Result(property = "totalprice",column = "totalprice"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "uniqueId",column = "uniqueId"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "status",column = "status"),
            @Result(property = "product",column = "pid",javaType = com.example.shopping1.entities.Product.class,
                    one = @One(select = "com.example.shopping1.mapper.ProductMapper.queryOne"))
    })
    public Order queryOne(int uid, int pid);

    @Insert("insert into order01(onum,totalprice,uid,status,pid) " +
            "values(#{onum},#{totalprice},#{uid},#{status},#{product.pid})")
    public void addOne(Order order) ;


    @Select("select * from order01 where uid=#{uid}")
    @Results(value = {
            @Result(property = "oid",column = "oid"),
            @Result(property = "onum",column = "onum"),
            @Result(property = "totalprice",column = "totalprice"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "uniqueId",column = "uniqueId"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "status",column = "status"),
            @Result(property = "product",column = "pid",javaType = com.example.shopping1.entities.Product.class,
                    one = @One(select = "com.example.shopping1.mapper.ProductMapper.queryOne"))
    })
    List<Order> queryByUid(int uid);


    @Select("delete from order01 where oid = #{oid}")
    void deleteByOid(int oid);

    @Select("select * from order01 where oid = #{oid}")
    @Results(value = {
            @Result(property = "oid",column = "oid"),
            @Result(property = "onum",column = "onum"),
            @Result(property = "totalprice",column = "totalprice"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "uniqueId",column = "uniqueId"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "status",column = "status"),
            @Result(property = "product",column = "pid",javaType = com.example.shopping1.entities.Product.class,
                    one = @One(select = "com.example.shopping1.mapper.ProductMapper.queryOne"))
    })
    Order queryByOid(int oid);

    @Update("update order01 set onum = #{newNum} where oid = #{oid}")
    void alterNum(int oid, int newNum);

    @Update("update order01 set totalprice = #{totalprice} where oid = #{oid}")
    void alterTotalPrice(Double totalprice,int oid);
}
