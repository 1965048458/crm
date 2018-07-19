package com.xuebei.crm.login;

import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
@Mapper
public interface LoginRegisterMapper {

    void insertUser(User user);

    User searchTel(@Param("tel") String tel);

    void changePwd(@Param("tel") String tel, @Param("pwd") String pwd);
}
