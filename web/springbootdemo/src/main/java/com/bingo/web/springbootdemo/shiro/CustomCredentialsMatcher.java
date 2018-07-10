package com.bingo.web.springbootdemo.shiro;

import com.bingo.web.springbootdemo.properties.GlobalProperties;
import com.bingo.web.springbootdemo.utils.AESUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private GlobalProperties globalProperties;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Object tokenCredentials;
        try {
            // 使用AES对密码进行加密后，与数据库中的密码进行对比
            tokenCredentials = AESUtils.base64Encrypt(String.valueOf(token.getPassword()), globalProperties.getShiroSecret());
        } catch (Exception e) {
            tokenCredentials = null;
        }
        Object accountCredentials = getCredentials(authenticationInfo);
        return equals(tokenCredentials, accountCredentials);
    }

}
