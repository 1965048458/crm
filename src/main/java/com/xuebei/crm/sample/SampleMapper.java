package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
@Mapper
public interface SampleMapper {
    List<User> searchUser(@Param("keyword") String keyword);

    void insertUser(User user);

    void editUser(User user);
}
