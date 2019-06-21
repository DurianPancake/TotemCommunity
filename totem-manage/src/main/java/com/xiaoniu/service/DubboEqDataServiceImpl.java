package com.xiaoniu.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoniu.mapper.EqDataMapper;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.dubbo.DubboEqDataService;

/**
 * 
 * @author GFL
 *
 */
@Service(timeout=3000)
public class DubboEqDataServiceImpl implements DubboEqDataService {

	@Autowired
	private EqDataMapper eqDataMapper;
	
	/**
	 * @author GFL
	 * 	根据时间查找地震数据
	 */
	@Override
	public List<EqData> findEqDataByTime(Integer day) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - day);
        Date d = c.getTime();
		QueryWrapper<EqData> queryWrapper = new QueryWrapper<EqData>();
		queryWrapper.gt("occur_time", d).lt("occur_time", new Date());
		List<EqData> list = eqDataMapper.selectList(queryWrapper);
		return list;
	}

}
