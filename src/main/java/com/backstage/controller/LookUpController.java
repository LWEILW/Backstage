package com.backstage.controller;

import com.backstage.service.ShareService;
import com.backstage.util.Result;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GlobalController
 * @Description: 通用接口
 * @Author LW
 * @Date 2020-07-30
 **/
@RestController
@RequestMapping("/global")
public class LookUpController {
    private static final Logger log = LoggerFactory.getLogger(LookUpController.class);

    @Autowired
    private ShareService shareService;

    /**
     * 登出
     *
     * @return
     */
    @GetMapping(value = "/loginOut")
    public Result loginOut() {
        SecurityUtils.getSubject().logout();
        System.out.println("无权限");
        return Result.fail("没有权限");
    }

    23421342142134
}
