package com.xiaoniu.service.dubbo;

import com.github.pagehelper.PageInfo;
import com.xiaoniu.pojo.Comment;

import java.util.List;
import java.util.Map;

/**
 * @Author: LLH
 * @Date: 2019/6/23 9:55
 */
public interface DubboCommentService {

    /**
     * 查询后台评论列表
     * @param pageIndex
     * @return
     */
    PageInfo<Comment> findSysCommentDataPage(Integer pageIndex);

    /**
     * 查询前台评论列表
     * @param pageIndex
     * @return
     */
    PageInfo<Map<String, Object>> findCommentPageList(Integer pageIndex);

    /**
     * 新增回复
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

    /**
     * 查询子列表数据
     * @param commentId
     * @return
     */
    List<Map<String, Object>> findSubCommentListById(Long commentId);
}
