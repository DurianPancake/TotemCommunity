package com.xiaoniu.test;

import com.xiaoniu.mapper.UserAuthMapper;
import com.xiaoniu.pojo.UserAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/21 11:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestInsertAuth {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Test
    public void test1() {
        UserAuth auth = new UserAuth();
        auth.setAuthName("爬虫管理")
                .setAuthValue(1)
                .setBaseFlag(true)
                .setRemark("基本爬虫功能的管理");
        int insert = userAuthMapper.insert(auth);
        if (insert > 0) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }

    @Test
    public void testSelect() {
        List<UserAuth> auths = userAuthMapper.selectList(null);
        System.out.println(auths);
    }
}
