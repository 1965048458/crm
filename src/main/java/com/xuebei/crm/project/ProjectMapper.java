package com.xuebei.crm.project;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/7/24.
 */
@Mapper
public interface ProjectMapper {

    void insertProject(Project project);
}
