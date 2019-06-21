package com.xiaoniu;

import com.xiaoniu.pojo.EqData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 11:17
 */
public class TestCrawler {

    @Test
    public void test1() throws Exception {

        Document doc = Jsoup.connect("http://ditu.92cha.com/dizhen.php?page=1")
                .data("query", "java")
                .userAgent("chrome")
                .cookie("auth", "token")
                .timeout(5000)
                .post();
        Elements trs = doc.select("div.table-responsive").select("table").select("tr");

        /*List<String> list = trs.eachText();
        System.out.println(list);*/

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
            eqDataList.add(new EqData(tdList));
        }

        for (EqData eqData : eqDataList) {
            System.out.println(eqData);
        }
    }
}
