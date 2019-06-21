package com.xiaoniu.constant;

/**
 * @Author: LLH
 * @Date: 2019/6/17 16:11
 */
public interface BasicConst {

    /** 二级域名 */
    String SECOND_DOMAIN_NAME = "xiaoniu.com";

    /** Token过期时间 */
    int TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60;

    String TICKET_COOKIE_NAME = "TOTEM_TICKET";

    /** 地震源数据URL */
    String SOURCE_URL = "http://ditu.92cha.com/dizhen.php?page=";

    /** 用来验证最新地震数据Redis用的键 */
    String LASTEST_DATA = "LATEST_DATA";

    /** 请求登录的路径(未确定) */
    String LOGIN_URL = "/user/login.html";
}
