package com.xuebei.crm.message;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Mapper
public interface MsgMapper {

    List<Query> searchQueryList(@Param("userId")String userId);

    List<Query> searchEnclosureApplyList(@Param("userId") String userId);

    List<Query> searchEnclosureDelayApplyList(@Param("userId") String userId);

    List<Query> searchProjectApplyList(@Param("userId")String userId);
}
