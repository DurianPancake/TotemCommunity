package com.xiaoniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoniu.pojo.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: LLH
 * @Date: 2019/6/23 10:47
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询前端评论
     * @param pageOffset
     * @return
     * @author LLH
     */
    List<Map<String, Object>> selectWebComments(int pageOffset);

    /**
     * 获取下一个自增的ID
     * @return
     */
    @Select("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'comment'")
    Long selectNextId();
}
