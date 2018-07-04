package com.bingo.web.springbootdemo.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object tokenCredentials;
        try {
            // 使用AES对密码进行加密后，与数据库中的密码进行对比
            tokenCredentials = null;
            //tokenCredentials = AESUtils.base64Encrypt(String.valueOf(token.getPassword()), globalProperties.getShirosecret());
        } catch (Exception e) {
            tokenCredentials = null;
        }
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredentials, accountCredentials);
    }

}
