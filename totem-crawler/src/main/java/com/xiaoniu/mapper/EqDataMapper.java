package com.xiaoniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoniu.pojo.EqData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 14:19
 */
public interface EqDataMapper extends BaseMapper<EqData> {

    /**
     * 插入多条数据
     * @param activeList
     */
    void insertDataList(List<EqData> activeList);

    /**
     * 查询一条数据
     * @return
     */
    @Select("SELECT * FROM eq_data order by occur_time desc limit 1")
    List<EqData> selectLatestOne();
}
