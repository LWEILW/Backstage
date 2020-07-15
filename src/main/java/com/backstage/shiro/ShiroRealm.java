package com.backstage.shiro;//package com.blogger.shiro;


import com.backstage.dao.admin.UserMapper;
import com.backstage.entity.admin.Permission;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Shiro配置文件
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapperEx;

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    /**
     * 登录认证,用来验证当前登录的用户，获取认证信息。
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---------登录认证----------");
        // 查出是否有此用户
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 用户名称
        String userName = token.getUsername();
        // 用户密码
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        // 根据用户名获取用户信息
        User user = userMapperEx.selectAllByName(userName);
        // 数据库获取用户密码
        String userPassword = user.getUserPassword();
        String salt = user.getSalt();
        // mb5解密
        String password = new Md5Hash(userPwd, salt, 2).toString();

        logger.info("获取到token为:" + token.toString() + "username:" + userName);

        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!password.equals(userPassword)) {
            throw new AccountException("密码不正确");
        } else if (user.getUserStatus() == 0) {
            throw new DisabledAccountException("帐号已经禁止登录！");
        } else if (user != null) {
            // 更新登录时间 last login time
            // user.setCreateDate(new Date());
            // 更新用户方法
            // sysUserService.updateById(user);

            // 用户的角色集合
            List<String> roleStrlist = new ArrayList<String>();
            // 用户的权限集合
            List<String> perminsStrlist = new ArrayList<String>();
            // 获取用户角色
            List<Role> roleList = userMapperEx.getRoleListByUserId(user.getUserId());
            for (Role role : roleList) {
                roleStrlist.add(role.getRoleName());
                //获取用户权限
                List<Permission> permissionList = userMapperEx.getPermissionListByRoleId(role.getRoleId());
                for (Permission uPermission : permissionList) {
                    perminsStrlist.add(uPermission.getPermissionName());
                }
            }

            user.setRoleList(roleStrlist);
            user.setPermissionList(perminsStrlist);

            // Session session = SecurityUtils.getSubject().getSession();
            // session.setAttribute("user", user);//成功则放入session

            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, userPassword, credentialsSalt, getName());


            // 验证成功开始踢人(清除缓存和Session)
            // deleteCache(userName, true);

            // 认证成功后更新token
//            user.setToken(SecurityUtils.getSubject().getSession().getId().toString());
//            userMapperEx.updateUser(user);

            return authenticationInfo;
        }

        return null;
    }

    /**
     * 权限认证
     * 为当前登录成功的用户授予权限和分配角色。
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("---------权限认证----------");
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 用户的角色集合
            info.addRoles(user.getRoleList());
            // 用户的权限集合
            info.addStringPermissions(user.getPermissionList());

            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

}
