package com.example.shopping1.mapper;

import com.example.shopping1.entities.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("insert into product(pname,price,productIntroduce,sales,sid,pImgUrl) " +
            "values(#{pname},#{price},#{productIntroduce},#{sales},#{sid},#{pImgUrl})")
    public void addProduct(Product product);

    @Select("select * from product where sid = #{sid}")
    List<Product> queryAll(int sid);

    @Delete("delete from product where pid = #{pid}")
    void delete(int pid);

    @Select("select * from product")
    List<Product> queryAllPro();

    @Select("select * from product where pid = #{pid}")
    Product queryOne(Integer pid);

    @Select("select * from product where pname = #{pname}")
    List<Product> queryByName(String name);


}
