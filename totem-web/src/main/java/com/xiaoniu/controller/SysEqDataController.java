package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.dto.EqDataDTO;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.dubbo.DubboEqDataService;
import com.xiaoniu.util.ExcelUtil;
import com.xiaoniu.util.ValidUtil;
import com.xiaoniu.vo.SysResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/22 14:27
 */
@RequestMapping("sys/earthquake")
@RestController
public class SysEqDataController {

    @Reference(timeout = 4000)
    private DubboEqDataService eqDataService;

    /**
     * 后台查询地震数据页
     * @param pageIndex
     * @param eqData
     * @return
     */
    @RequestMapping("page/{pageIndex}")
    public SysResult findEaDataPage(@PathVariable Integer pageIndex, EqDataDTO eqData) {
        return SysResult.success(eqDataService.findEqData4SysByLimit(pageIndex, eqData));
    }

    /**
     * 导出Excel
     * @param eqDataDTO
     */
    @GetMapping("export")
    public void exportExcelEqData(EqDataDTO eqDataDTO, HttpServletResponse response) {
        List<EqData> dataList = eqDataService.getEqData4Excel(eqDataDTO);
        StringBuilder title = new StringBuilder("地震数据导出");
        SimpleDateFormat format = new SimpleDateFormat(BasicConst.STANDARD_DATE_FORMAT);
        appendTime(eqDataDTO.getStartTime(), title, format);
        title.append("$");
        appendTime(eqDataDTO.getEndTime(), title, format);
        title.append("-" + System.currentTimeMillis());
        title.append(".xlsx");
        ExcelUtil.exportExcel(dataList, "地震数据", "数据页", EqData.class, title.toString(), response);
    }

    private void appendTime(Date date, StringBuilder title, SimpleDateFormat format) {
        if (ValidUtil.isNotEmpty(date)) {
            title.append(format.format(date));
        } else {
            title.append("$");
        }
    }
}
