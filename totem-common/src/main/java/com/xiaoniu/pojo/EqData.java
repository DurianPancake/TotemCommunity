package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
public class EqData extends BasePojo implements Comparable<EqData>{

    private static SimpleDateFormat NORMAL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TableId(type = IdType.AUTO)
    private Long id;
    private Double magnitude;
    private Date occurTime;
    private String longitude;
    private String latitude;
    private Integer depth;
    private String location;
    private Date createTime;

    public EqData(List<String> tdList) throws ParseException {
        setOccurTime(NORMAL_FORMAT.parse(tdList.get(0)));
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
                ", createTime=" + NORMAL_FORMAT.format(createTime) +
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
