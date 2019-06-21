package com.xiaoniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoniu.pojo.EqData;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 14:19
 */
public interface EqDataMapper extends BaseMapper<EqData> {

    /**
     *
     * @param activeList
     */
    void insertDataList(List<EqData> activeList);
}
