package com.bingo.web.springbootdemo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class MyShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        if (username == null) {
            throw new AccountException("请输入登录账号！");
        }
        /*ConsoleUser user = userService.findByUsername(username, globalProperties.getProjectcode());
        if (user == null) {
        	throw new UnknownAccountException("您所输入的帐号[" + username + "]不存在或者已被删除！");
        }*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(null, "", getName());
        return info;
    }

}
