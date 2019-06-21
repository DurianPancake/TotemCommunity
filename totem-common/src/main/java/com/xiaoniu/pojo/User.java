package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 暂时的User类
 * @Author: LLH
 * @Date: 2019/6/13 14:45
 */
@Data
@TableName("sys_user")
@Accessors(chain = true)
public class User extends BasePojo{

    @TableId(type = IdType.AUTO)
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private String roleName;
    private Integer roleAuth;
    private String headImg;
    // 禁用状态
    private Boolean status;
    // 删除状态
    private Boolean delFlag;
}
