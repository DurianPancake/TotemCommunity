package com.xiaoniu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoniu.mapper.CommentMapper;
import com.xiaoniu.pojo.Comment;
import com.xiaoniu.service.dubbo.DubboCommentService;
import com.xiaoniu.util.PageUtil;
import com.xiaoniu.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: LLH
 * @Date: 2019/6/23 9:57
 */
@Service(timeout = 3000)
public class CommentServiceImpl implements DubboCommentService {

    @Autowired
    private CommentMapper commentMapper;

    private final static int WEB_COMMENT_PAGE_SIZE = 5;

    /**
     * 查询后台评论列表
     * @param pageIndex
     * @return
     */
    @Override
    public PageInfo<Comment> findSysCommentDataPage(Integer pageIndex) {
        return null;
    }

    /**
     * 查询前台评论列表
     * @param pageIndex
     * @return
     * @author LLH
     */
    @Override
    public PageInfo<Map<String, Object>> findCommentPageList(Integer pageIndex) {
        // 校验
        ValidUtil.validatePageIndex(pageIndex);
        //
        List<Map<String, Object>> comments = commentMapper
                .selectWebComments(PageUtil.getPageOffset(pageIndex, WEB_COMMENT_PAGE_SIZE));

        // 封装数据
        PageHelper.startPage(pageIndex, WEB_COMMENT_PAGE_SIZE);
        return new PageInfo<>(comments);
    }

    /**
     * 新增回复
     * @param comment 如果是顶级评论 pid写0 和 tid不写，
     *                如果是回复评论，pid写被回复的id，tid写被回复的评论的tid
     * @return
     * @author LLH
     */
    @Transactional
    @Override
    public int insertComment(Comment comment) {
        // 校验参数
        ValidUtil.requireNotNull(comment.getPid());
        ValidUtil.requireNotNull(comment.getComment());
        if (comment.getPid() == 0 && comment.getTid() == null) {
            // 处理顶级评论
            Long nextId = commentMapper.selectNextId();
            comment.setId(nextId)
                    .setTid(nextId);
        } else {
            // 处理回复评论
            ValidUtil.requireNotNull(comment.getTid());
        }
        comment.setCreateTime(new Date());
        return commentMapper.insert(comment);
    }

    /**
     * 查询子列表数据
     * @param commentId
     * @return
     * @author LLH
     */
    @Override
    public List<Map<String, Object>> findSubCommentListById(Long commentId) {
        // 校验
        ValidUtil.requireNotNull(commentId);
        //
        List<Map<String, Object>> comments = commentMapper.selectSubCommentList(commentId);
        return null;
    }
}
