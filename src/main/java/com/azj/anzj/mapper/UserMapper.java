package com.azj.anzj.mapper;

import com.azj.anzj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User findByUsernameAndPassword(String username,String password);
    List<User> queryAllUser();
    User queryUserById(int id);
    int addUser(User user);
    int deleteUser(int id);
    int updateUser(User user);
}
