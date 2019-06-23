package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: LLH
 * @Date: 2019/6/23 10:05
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("comment")
public class Comment extends BasePojo{

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pid;
    private Long tid;
    private Long userId;
    private String comment;
    private Date createTime;
}
