package com.xuebei.crm.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgMapper msgMapper;

    @Override
    public List<Query> searchQueryList(String userId) {
        List<Query> queryList = msgMapper.searchQueryList(userId);
        return queryList;
    }
}
