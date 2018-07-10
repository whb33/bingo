package com.bingo.web.springbootdemo.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 请求返回代码枚举
 */
public enum RespCode {
    //抽奖
    DRAW_WIN_OVER_LIMIT("-3", "中奖限制"),
    DRAW_CHANCE_OVER_LIMIT("-2", "抽奖限制"),
    DRAW_MISS("-1", "抽奖不命中"),
    DRAW_HIT("1", "抽奖命中"),
    DRAW_NOT_PRIZES("0", "奖品派发完毕"),
    //通用
    EXISTS("3", "存在"),
    REFUSE("2", "拒绝"),
    SUCCESS("1", "成功"),
    FAIL("0", "失败"),
    ERROR("-1", "异常"),
    //登录
    UN_LOGIN("-10", "未登录"),
    OVERTIME_LOGIN("-11", "登录超时"),
    //活动时间
    OUT_OF_DATE_RANGE("-20", "未在活动总时间范围内"),
    OUT_OF_DAILY_TIME_RANGE("-21", "未在每日活动时间范围内"),
    BEFORE_DATE_RANGE("-22", "活动未开始"),
    AFTER_DATE_RANGE("-23", "活动已结束"),
    //浏览器
    NOT_PC_BROWSER("-30", "非PC端浏览器"),
    NOT_WAP_BROWSER("-31", "非WAP端浏览器"),
    NOT_WE_CHAT_BROWSER("-32", "非微信浏览器"),
    NOT_FEI_XIN_BROWSER("-33", "非飞信浏览器"),
    //短信验证码
    VERIFICATION_CODE_SWITCH_OFF("-40", "短信验证码开关已关闭"),
    VERIFICATION_CODE_WRONG("-41", "短信验证码错误"),
    VERIFICATION_CODE_OVER_MIN_LIMIT("-42", "短信验证码超过分钟上限"),
    VERIFICATION_CODE_OVER_DAY_LIMIT("-43", "短信验证码超过每天上限"),
    //手机号码
    MOBILE_IS_NULL("-50", "手机号码为空"),
    NOT_MOBILE("-51", "手机格式不正确"),
    NOT_CHINA_MOBILE("-52", "非中国移动号码"),
    CHINA_MOBILE_PRIVATE_LIMIT("-53", "移动内部成员手机号码限制"),
    //微信
    NOT_WE_CHAT_AUTHORIZE("-60", "未微信授权"),
    NOT_WE_CHAT_SUBSCRIBE("-61", "未关注"),
    WE_CHAT_EXISTS("-62", "微信账号已存在"),
    NOT_WE_CHAT_FIRST_SUBSCRIBE("-63", "非首次关注"),
    WE_CHAT_PHONE_BOUND("-64","微信已与手机号码绑定"),
    PHONE_WE_CHAT_BOUND("-65","手机号码已与微信绑定"),
    //校验数据
    EXIST_NULL_PARAM("-70","存在参数为空"),
    //后台
    USER_LOCK("-1","用户被锁定，拒绝登录"),
    USER_NOUSE("-1","用户被停用，拒绝登录"),
    USER_NOTFOUND("-1","输入用户名或密码错误"),
    CAPTCHA_MISMATCH("-5","验证码错误"),
    MISMATCH_PASSWD("-3","用户名或密码错误"),
    BAD_SQL("-10","无效的SQL语句"),
    QUERY_SQL_INVALID("-11","SQL语句非法，只允许select查询语句"),
    //请求
    FREQUENT_VISIT("-800", "请求频繁"),
    IP_FREQUENT_VISIT("-801", "地址请求频繁"),
    //其他
    UNKNOWN_RETURN_CODE("-1000", "未知返回码");

    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    RespCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RespCode selCode(String code) {
        if (StringUtils.isBlank(code)) {
            return ERROR;
        }
        for (RespCode c : RespCode.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        RespCode unknown = UNKNOWN_RETURN_CODE;
        unknown.setMsg(UNKNOWN_RETURN_CODE.getMsg() + ":" + code);
        return unknown;
    }

    public boolean isUnsuccessful(){
        return !RespCode.SUCCESS.equals(this);
    }

    @Override
    public String toString() {
        return "RespCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
