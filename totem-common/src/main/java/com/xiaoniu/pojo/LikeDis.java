package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoniu.constant.enums.DoLikeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @Author: LLH
 * @Date: 2019/6/23 10:08
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("like_dis")
public class LikeDis extends BasePojo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long commentId;
    private Long userId;
    private DoLikeEnum operateType;
    private Date createTime;

    /**
     * 枚举的Set方法
     * @param type
     */
    public void setOperateType(String type) {
        Converter<String, DoLikeEnum> converter = new Converter<String, DoLikeEnum>() {

            @Override
            public DoLikeEnum convert(String source) {
                Class<DoLikeEnum> targetClass = DoLikeEnum.class;
                DoLikeEnum[] types = targetClass.getEnumConstants();
                for (DoLikeEnum type : types) {
                    if (type.toString().equals(source.toUpperCase())) {
                        return type;
                    }
                }
                return null;
            }
        };
        this.operateType = converter.convert(type);
    }

    /**
     * 枚举的get方法
     * @return
     */
    /*public DoLikeEnum getOperateType() {
        return operateType.toString().toLowerCase();
    }*/
}
