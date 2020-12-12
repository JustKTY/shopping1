package com.example.shopping1.mapper;

import com.example.shopping1.entities.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where aname=#{username} and apassword=#{password}")
    Admin findByUnameAndPsd(String username, String password);

}
