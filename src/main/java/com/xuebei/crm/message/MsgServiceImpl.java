package com.xuebei.crm.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2018/8/21.
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgMapper msgMapper;

    @Override
    public List<Apply> applyList(String userId) {
        List<Apply> applyList = new ArrayList<>();
        List<Apply> enclosureApplyList = enclosureApplyList(userId);
        List<Apply> enclosureDelayApplyList = enclosureDelayApplyList(userId);
        applyList.addAll(enclosureApplyList);
        applyList.addAll(enclosureDelayApplyList);
        applyList.sort((o1, o2) -> o1.getApplyTime().before(o2.getApplyTime())?1:-1);
        return applyList;
    }

    private List<Apply> enclosureApplyList(String userId){
        List<Apply> enclosureApplyList = msgMapper.searchEnclosureApply(userId);
        for(Apply apply:enclosureApplyList){
            apply.setApplyType(ApplyTypeEnum.ENCLOSURE_APPLY);
        }
        return enclosureApplyList;
    }

    private List<Apply> enclosureDelayApplyList(String userId){
        List<Apply> enclosureDelayApplyList = msgMapper.searchEnclosureDelayApply(userId);
        for(Apply apply:enclosureDelayApplyList){
            apply.setApplyType(ApplyTypeEnum.ENCLOSURE_DELAY_APPLY);
        }
        return enclosureDelayApplyList;
    }

    private List<Apply> projectApplyList(String userId){
        return null;
    }

    @Override
    public void applyCheck(ApplyTypeEnum applyType, String applyId, Boolean isApprove,String userId) {
        switch (applyType){
            case ENCLOSURE_APPLY:
                if(isApprove){
                    msgMapper.enclosureApplyAgree(applyId,userId);
                }else {
                    msgMapper.enclosureApplyDecline(applyId,userId);
                }
                break;
            case ENCLOSURE_DELAY_APPLY:
                if(isApprove){
                    msgMapper.enclosureDelayApplyAgree(applyId,userId);
                    msgMapper.enclosureApplyEndTsExtend(applyId,userId);
                }else {
                    msgMapper.enclosureDelayApplyDecline(applyId,userId);
                }
                break;
            default:
                break;
        }
    }


}
