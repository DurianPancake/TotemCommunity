package com.xiaoniu.dto;

import com.xiaoniu.pojo.EqData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LLH
 * @Date: 2019/6/22 14:33
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EqDataDTO extends EqData implements Serializable {

    /** 定义了查询使用的起止时间，震级范围 */
    private Date startTime;
    private Date endTime;
    private Double minMagnitude;
    private Double maxMagnitude;
}
