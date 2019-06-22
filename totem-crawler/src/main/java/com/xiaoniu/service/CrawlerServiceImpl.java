package com.xiaoniu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.mapper.EqDataMapper;
import com.xiaoniu.pojo.EqData;
import com.xiaoniu.service.dubbo.DubboCrawlerService;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 14:10
 */
@Service(timeout = 5000)
public class CrawlerServiceImpl implements DubboCrawlerService {

    private Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private EqDataMapper eqDataMapper;

    /**
     * 执行爬虫主方法：
     * 1. 每爬取一页，遍历查询结果，比对获取有效数据
     * 将有效数据批量入库
     * 2. 如果全部为有效数据，则爬取下一页
     */
    @Override
    public void run() throws Exception {
        int page = 1;
        List<EqData> activeList = new ArrayList<>(50);

        // 取出最近的数据
        saveLatestData();
        String json = redisService.get(BasicConst.LASTEST_DATA);
        EqData lastestData = null;
        if (StringUtil.isNotEmpty(json)) {
            lastestData = ObjectMapperUtil.toObject(json, EqData.class);
        }

        /**
         * 主循环：获取数据
         */
        while (true) {

            Document doc = Jsoup.connect(BasicConst.SOURCE_URL + page)
                    .data("query", "java")
                    .userAgent("chrome")
                    .cookie("auth", "token")
                    .timeout(5000)
                    .post();
            Elements trs = doc.select("div.table-responsive").select("table").select("tr");

            /* 信息对象数据列表 */
            List<EqData> eqDataList = new ArrayList<>();

            // 遍历tr
            boolean jumpFirst = true;
            for (Element element : trs) {
                if (jumpFirst) {
                    jumpFirst = false;
                    continue;
                }
                // 获取td列表
                Elements tds = element.select("td");
                List<String> tdList = tds.eachText();
                try {
                    // 捕获解析异常，如果异常则舍弃
                    eqDataList.add(new EqData(tdList));
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            if (eqDataList.size() == 0) {
                return;
            }

            /** 检测对象列表 */
            // temp用来记载最近的数据
            EqData newLastestData = eqDataList.get(0);
            // 比较数据，如果没有新数据则
            if (newLastestData.compareTo(lastestData) <= 0) {
                return;
            }

            for (EqData eqData : eqDataList) {
                // 如果有新数据则加入
                if (eqData.compareTo(lastestData) > 0) {
                    activeList.add(eqData);
                } else {
                    // 如果不再有新数据则后处理，然后返回
                    // 将最新的数据缓存
                    saveLatestData();
                    // 入库
                    saveDataList(activeList);
                    logger.info(Thread.currentThread().getName() + "完成Crawler第"+page+"任务，共添加"
                            + activeList.size() + "条数据");
                    return;
                }
            }
            saveDataList(activeList);
            activeList.clear();
            // 获取下一页数据
            page++;
            Thread.sleep(100);
        }
    }

    /** 存入最新的数据 */
    private void saveLatestData() throws JsonProcessingException {
        redisService.del(BasicConst.LASTEST_DATA);
        List<EqData> dataList = eqDataMapper.selectLatestOne();
        if (dataList.size() > 0) {
            EqData data = dataList.get(0);
            String dataJson = ObjectMapperUtil.toJson(data);
            redisService.set(BasicConst.LASTEST_DATA, dataJson);
            logger.info("============== 最近数据已加载 ===============");
        }
    }

    /**
     * 存储数据入库
     * @param activeList
     */
    private void saveDataList(List<EqData> activeList) {
        eqDataMapper.insertDataList(activeList);
    }
}