package com.xiaoniu.service.dubbo;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xiaoniu.dto.EqDataDTO;
import com.xiaoniu.pojo.EqData;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author GFL
 * 	地图资源
 *
 */
public interface DubboEqDataService {

	List<EqData> findEqDataByTime(Integer day, Integer number);

    /**
     * 后台查询地震数据页
     * @param pageIndex：页索引
     * @param eqData： DTO，封装了查询条件，包括震级范围、时间范围
     * @return
     * @author LLH
     */
    PageInfo<EqData> findEqData4SysByLimit(Integer pageIndex, EqDataDTO eqData);

    /**
     * 导出数据到Excel
     * @param eqDataDTO
     * @author LLH
     */
    List<EqData> getEqData4Excel(EqDataDTO eqDataDTO);
}
