package com.xiaoniu.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 11:12
 */
@Data
@TableName("eq_data")
@NoArgsConstructor
public class EqData extends BasePojo implements Comparable<EqData>, Serializable {

    private static SimpleDateFormat NORMAL_FORMAT = new SimpleDateFormat(BasicConst.STANDARD_TIME_FORMAT);

    @TableId(type = IdType.AUTO)
    private Long id;
    @Excel(name = "震级(M)", orderNum = "3")
    private Double magnitude;
    @Excel(name = "发震时刻(UTC+8)", exportFormat = BasicConst.STANDARD_TIME_FORMAT, orderNum = "2")
    private Date occurTime;
    @Excel(name = "经度(°)", orderNum = "4")
    private String longitude;
    @Excel(name = "纬度(°)", orderNum = "5")
    private String latitude;
    @Excel(name = "深度(千米)", orderNum = "6")
    private Integer depth;
    @Excel(name = "参考位置", orderNum = "1")
    private String location;
    private Date createTime;

    public EqData(List<String> tdList) throws ParseException {
        if (StringUtil.isNotBlank(tdList.get(0))) {
            Date occur = NORMAL_FORMAT.parse(tdList.get(0));
            if (occur.getTime() > new Date().getTime()) {
                throw new ParseException("", 0);
            }
            setOccurTime(occur);
        }
        setMagnitude(Double.valueOf(tdList.get(1)));
        setLongitude(tdList.get(2));
        setLatitude(tdList.get(3));
        String depth = tdList.get(4);
        setDepth(Integer.valueOf(depth.substring(0, depth.indexOf("."))));
        String location = tdList.get(5);
        setLocation(location.substring(0, Math.min(128, location.length())));
    }

    @Override
    public String toString() {
        return "EqData{" +
                "id=" + id +
                ", magnitude=" + magnitude +
                ", occurTime=" + occurTime +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", depth=" + depth +
                ", location='" + location + '\'' +
                '}';
    }

    /**
     * 比较大小，更靠近现在的信息值更大
     * @param o 目标data
     * @return 正数 零 负数
     */
    @Override
    public int compareTo(EqData o) {
        if (o == null) {
            return 1;
        }
        return (int) (this.getOccurTime().getTime() - o.getOccurTime().getTime());
    }
}
