package com.xuebei.crm.message;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Mapper
public interface MsgMapper {
    //圈地申请
    List<Apply> searchEnclosureApply(@Param("userId") String userId);
    //延期申请
    List<Apply> searchEnclosureDelayApply(@Param("userId") String userId);
    //项目启动申请
    List<Apply> searchProjectApply(@Param("userId") String userId);

    //同意圈地申请
    void enclosureApplyAgree(@Param("enclosureApplyId") String enclosureApplyId,
                             @Param("userId")String userId);
    //拒绝圈地申请
    void enclosureApplyDecline(@Param("enclosureApplyId") String enclosureApplyId,
                               @Param("userId")String userId);
    //同意延期申请
    void enclosureDelayApplyAgree(@Param("enclosureDelayApplyId") String enclosureDelayApplyId,
                                  @Param("userId")String userId);
    //同意延期申请后，对应的申请延长三个月
    void enclosureApplyEndTsExtend(@Param("enclosureDelayApplyId") String enclosureDelayApplyId,
                                   @Param("userId")String userId);
    //拒绝延期申请
    void enclosureDelayApplyDecline(@Param("enclosureDelayApplyId") String enclosureDelayApplyId,
                                    @Param("userId")String userId);

}
