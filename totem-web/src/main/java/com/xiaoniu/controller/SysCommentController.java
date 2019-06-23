package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.service.dubbo.DubboCommentService;
import com.xiaoniu.vo.SysResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LLH
 * @Date: 2019/6/23 9:51
 */
@RestController
@RequestMapping("sys/comment")
public class SysCommentController {

    @Reference(timeout = 3000, check = false)
    private DubboCommentService commentService;

    @GetMapping("page/{pageIndex}")
    public SysResult findCommentDataPage(@PathVariable Integer pageIndex) {
        return SysResult.success(commentService.findSysCommentDataPage(pageIndex));
    }
}
