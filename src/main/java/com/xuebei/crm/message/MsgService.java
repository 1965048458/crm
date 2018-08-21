package com.xuebei.crm.message;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
public interface MsgService {

    List<Query> searchQueryList(String userId);
}
