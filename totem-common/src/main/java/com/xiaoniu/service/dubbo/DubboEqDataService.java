package com.xiaoniu.service.dubbo;

import java.util.List;

import com.xiaoniu.pojo.EqData;

/**
 * 
 * @author GFL
 * 	地图资源
 *
 */
public interface DubboEqDataService {

	List<EqData> findEqDataByTime(Integer day);
}
