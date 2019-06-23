package com.xiaoniu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoniu.constant.enums.DoLikeEnum;
import com.xiaoniu.mapper.LikeDisMapper;
import com.xiaoniu.pojo.LikeDis;
import com.xiaoniu.service.dubbo.DubboLikeDisService;
import com.xiaoniu.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author: LLH
 * @Date: 2019/6/23 15:03
 */
@Service
public class LikeDisServiceImpl implements DubboLikeDisService {

    @Autowired
    private LikeDisMapper likeDisMapper;
    private final static String OPERATE_TYPE = "operate_type";

    /**
     * 点赞/取消点赞
     *  有数据删除表示取消点赞，没有数据新增表示新增点赞
     * @param likeDis: 包含用户ID，点赞类型，评论ID
     * @return
     * @author LLH
     */
    @Override
    public int likeDisService(LikeDis likeDis) {
        // 参数校验
        ValidUtil.requireNotNull(likeDis.getCommentId());
        ValidUtil.requireNotNull(likeDis.getOperateType());
        // 查表
        QueryWrapper<LikeDis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", likeDis.getUserId());
        queryWrapper.eq(OPERATE_TYPE, likeDis.getOperateType());
        LikeDis target = likeDisMapper.selectOne(queryWrapper);
        // 新增：
        if (target == null) {
            likeDis.setCreateTime(new Date());
            return likeDisMapper.insert(likeDis);
        }
        // 删除：
        return likeDisMapper.deleteById(target.getId());
    }
}
