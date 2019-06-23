package com.xiaoniu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoniu.annotation.Cacheable;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.constant.enums.KeyType;
import com.xiaoniu.dto.EqDataDTO;
import com.xiaoniu.mapper.EqDataMapper;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.dubbo.DubboEqDataService;
import com.xiaoniu.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author GFL
 *
 */
@Service(timeout=3000)
public class DubboEqDataServiceImpl implements DubboEqDataService {

	@Autowired
	private EqDataMapper eqDataMapper;

	private final static String OCCUR_TIME = "occur_time";
	private final static String MAGNITUDE = "magnitude";

	/**
	 * @author GFL
	 * 	根据时间查找地震数据
	 */
	@Cacheable(key = BasicConst.WEB_EQDATA_KEY, seconds = 30, keyType=KeyType.FULL_ARGS)
	@Override
	public List<EqData> findEqDataByTime(Integer day, Integer number) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - day);
        Date d = c.getTime();
		QueryWrapper<EqData> queryWrapper = new QueryWrapper<EqData>();
		System.out.println(9);
		switch(number) {
		case 3:queryWrapper.lt(MAGNITUDE, 6);System.out.println(3);break;
		case 6:queryWrapper.gt(MAGNITUDE, 6).lt(MAGNITUDE, 8);System.out.println(6);break;
		case 9:queryWrapper.gt(MAGNITUDE, 8);System.out.println(9);break;
		}
		queryWrapper.gt(OCCUR_TIME, d).lt(OCCUR_TIME, new Date());
		List<EqData> list = eqDataMapper.selectList(queryWrapper);
		return list;
	}

	/**
	 * 后台查询地震数据页
	 * @param pageIndex ：页索引
	 * @param eqData    ： DTO，封装了查询条件，包括震级范围、时间范围
	 * @return PageHelper封装的数据页
	 * @author LLH
	 */
	@Cacheable(key = BasicConst.SYS_EQDATA_KEY, keyType = KeyType.FULL_ARGS, seconds = 60)
	@Override
	public PageInfo<EqData> findEqData4SysByLimit(Integer pageIndex, EqDataDTO eqData) {
		// 参数校验
		ValidUtil.validatePageIndex(pageIndex);
		// 查询数据
		List<EqData> dataList = getEqDataList(eqData);
		// 封装数据页
		PageHelper.startPage(pageIndex, BasicConst.SYS_PAGE_SIZE);
		PageInfo<EqData> pageInfo = new PageInfo<>(dataList);
		return pageInfo;
	}

	/** 查询数据列表 */
	private List<EqData> getEqDataList(EqDataDTO eqData) {
		// 封装查询条件
		QueryWrapper<EqData> queryWrapper = new QueryWrapper<>();

		if (eqData != null) {
			// 时间范围
			if (ValidUtil.isNotEmpty(eqData.getStartTime())) {
				queryWrapper.gt(OCCUR_TIME, eqData.getStartTime());
			}
			if (ValidUtil.isNotEmpty(eqData.getEndTime())) {
				queryWrapper.lt(OCCUR_TIME, eqData.getEndTime());
			}
			// 震级范围
			if (ValidUtil.isNotEmpty(eqData.getMaxMagnitude())) {
				queryWrapper.lt(MAGNITUDE, eqData.getMaxMagnitude());
			}
			if (ValidUtil.isNotEmpty(eqData.getMinMagnitude())) {
				queryWrapper.gt(MAGNITUDE, eqData.getMinMagnitude());
			}
		}
		queryWrapper.orderByDesc(OCCUR_TIME);
		return eqDataMapper.selectList(queryWrapper);
	}

	/**
	 * 导出数据到Excel
	 * @param eqDataDTO
	 * @author LLH
	 */
	@Override
	public List<EqData> getEqData4Excel(EqDataDTO eqDataDTO) {
		return getEqDataList(eqDataDTO);
	}
}
