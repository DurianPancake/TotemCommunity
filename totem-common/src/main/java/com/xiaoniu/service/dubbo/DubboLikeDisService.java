package com.xiaoniu.service.dubbo;

import com.xiaoniu.pojo.LikeDis;

/**
 * @Author: LLH
 * @Date: 2019/6/23 14:36
 */
public interface DubboLikeDisService {

    /**
     * 点赞/取消点赞
     * @param likeDis
     * @return
     */
    int likeDisService(LikeDis likeDis);
}
