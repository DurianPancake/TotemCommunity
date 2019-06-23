package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.pojo.Comment;
import com.xiaoniu.pojo.LikeDis;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.dubbo.DubboCommentService;
import com.xiaoniu.service.dubbo.DubboLikeDisService;
import com.xiaoniu.util.UserThreadLocal;
import com.xiaoniu.util.ValidUtil;
import com.xiaoniu.vo.SysResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: LLH
 * @Date: 2019/6/23 9:49
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Reference(timeout = 3000, check = false)
    private DubboCommentService commentService;
    @Reference(timeout = 3000, check = false)
    private DubboLikeDisService likeDisService;

    /**
     * 前端查询下拉数据页
     * @param pageIndex
     * @return
     * @author LLH
     */
    @GetMapping("main/{pageIndex}")
    public SysResult getCommentListData(@PathVariable Integer pageIndex) {
        return SysResult.success(commentService.findCommentPageList(pageIndex));
    }

    /**
     * 发表评论回复
     * @param comment 如果是顶级评论 pid写0 和 tid不写，
     *                如果是回复评论，pid写被回复的id，tid写被回复的评论的tid
     * @return
     * @author LLH
     */
    @PostMapping("reply/{pid}/{tid}")
    public SysResult addNewReply(Comment comment) {
        ValidUtil.requireNotNull(comment);
        User login = UserThreadLocal.get();
        comment.setUserId(login.getId());
        return SysResult.success(commentService.insertComment(comment));
    }

    /**
     * 获取子回复列表
     * @param commentId
     * @return
     * @author LLH
     */
    @GetMapping("replyList/{commentId}")
    public SysResult getReplyCommentListById(@PathVariable Long commentId) {
        return SysResult.success(commentService.findSubCommentListById(commentId));
    }

    /**
     * 点赞、踩功能
     * @param likeDis
     * @return
     * @author LLH
     */
    @PostMapping("reply/like")
    public SysResult changeLikeDisStatus(LikeDis likeDis) {
        ValidUtil.requireNotNull(likeDis);
        User login = UserThreadLocal.get();
        likeDis.setUserId(login.getId());
        return SysResult.success(likeDisService.likeDisService(likeDis));
    }
}
