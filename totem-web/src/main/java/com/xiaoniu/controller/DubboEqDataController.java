package com.xiaoniu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.dubbo.DubboEqDataService;

/**
 * 
 * @author GFL
 *	
 */
@Controller
@RequestMapping("ditu")
public class DubboEqDataController {

	@Reference(timeout=3000, check=false)
	private DubboEqDataService eqDataService;
	
	/**
	 * @author GFL
	 * @param day 根据时间查询地图数据，day=7，七天之内的数据
	 * @return
	 */
	@RequestMapping("findEqDataByTime")
	@ResponseBody
	public List<EqData> findEqDataByTime(Integer day) {
		List<EqData> list = eqDataService.findEqDataByTime(day);
		return list;
	}
}
