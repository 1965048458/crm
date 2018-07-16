package com.xuebei.crm.registerAndLogin;

import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
@Mapper
public interface registerAndLoginMapper {

    void insertUser(User user);

    List<User> searchTel(@Param("tel") String tel);

    void changePwd(@Param("tel") String tel, @Param("pwd") String pwd);
}
