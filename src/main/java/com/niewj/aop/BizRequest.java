package com.niewj.aop;

import com.google.gson.Gson;
import com.niewj.bean.User;

import java.util.Map;

public class BizRequest {

    /**
     * 请求远程数据: 假如 向某个接口请求一些数据, 然后返回一个用户信息;
     *
     * @param params 向第三方接口传递的参数
     * @return 第三方返回的数据：一般为json字符串
     */
    public String callData(Map<String, String> params) {
        if (params == null || !params.containsKey("idCard") || !params.containsKey("name") || !params.containsKey("phone")) {
            throw new IllegalArgumentException("name/idCard/phone三要素参数是必需的!");
        }

        User user = new User("niewj", "123456", true);
        return new Gson().toJson(user);
    }
}
