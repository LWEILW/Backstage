package com.backstage.config.shiro;

import com.backstage.util.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * shiro权限
 *
 * @Description:
 * @Date 2020-03-31 16:00
 * @Author Liu wei
 */
@ControllerAdvice
public class NoPermissionException {

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result handleShiroException(Exception ex) {

        return Result.fail("没有访问权限");
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Result AuthorizationException(Exception ex) {

        return Result.fail("权限认证失败");
    }
}
