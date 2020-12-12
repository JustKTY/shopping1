package com.example.shopping1.mapper;

import com.example.shopping1.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {



    @Insert("insert into user(username,password,telephone,email) values(#{userName},#{password},#{telephone},#{email})")
    public void addUser(User user);

    @Select("select * from user where username=#{userName}")
    User findByUsername(String userName);

    @Select("select * from user where username=#{username} and password=#{password}")
    User findByUnameAndPsd(String username, String password);
}
