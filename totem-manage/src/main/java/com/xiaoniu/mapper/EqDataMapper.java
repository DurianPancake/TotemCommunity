package com.xiaoniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.RedisService;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EqDataMapper extends BaseMapper<EqData> {

    /**
     * 查询一条数据
     * @return
     */
    @Select("SELECT * FROM eq_data order by occur_time desc limit 1")
    List<EqData> selectLatestOne();
}
