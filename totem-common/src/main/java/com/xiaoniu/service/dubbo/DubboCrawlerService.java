package com.xiaoniu.service.dubbo;

/**
 * @Author: LLH
 * @Date: 2019/6/20 14:08
 */
public interface DubboCrawlerService {

    /**
     * 执行爬虫主方法：
     * 1. 每爬取一页，遍历查询结果，比对获取有效数据
     *      将有效数据批量入库
     * 2. 如果全部为有效数据，则爬取下一页
     */
    void run() throws Exception;
}
