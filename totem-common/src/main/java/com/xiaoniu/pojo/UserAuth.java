package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: LLH
 * @Date: 2019/6/21 8:42
 */
@Data
@Accessors(chain = true)
@TableName("user_auth")
public class UserAuth extends BasePojo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String authName;
    private Integer authValue;
    private Boolean baseFlag;
    private String remark;

}
