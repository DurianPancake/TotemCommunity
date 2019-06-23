package com.xiaoniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoniu.constant.enums.DoLikeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

}
