package com.bingo.web.springbootdemo.constant;

import java.util.HashMap;
import java.util.Map;

public enum KeyStatus {

	SUCCESS(1, ""),
	/**
	 * 用户名不存在
	 */
	USER_NOTFOUND(-1, "输入用户名或密码错误"),
	/**
	 * 用户被锁定
	 */
	USER_LOCK(-1, "用户被锁定，拒绝登录"),
	/**
	 * 用户被停用
	 */
	USER_NOUSE(-1, "用户被停用，拒绝登录"),
	/**
	 * 用户被删除
	 */
	USER_DELE(-1, "用户不存在，拒绝登录"),
	/**
	 * 验证码错误
	 */
	CAPTCHA_MISMATCH(-5, "验证码错误"),
	/**
	 *
	 * 用户名和数字证书不匹配
	 */
	MISMATCH_USERKEY(-2, "用户名和数字证书不匹配"),
	/**
	 * 密码错误
	 */
	MISMATCH_PASSWD(-3, "用户名或密码错误"),
	/**
	 * 数字证书签名验证失败
	 */
	MISMATCH_SIGN(-4, "数字证书签名验证失败"),
	/**
	 * 重复登录
	 */
	MULTIPLE_LOGIN(-5, "重复登陆"),

	OTHER_ERR(-6, "其他错误"),;
	int code;
	String msg;
	Object attribute;// 附加属性

	private KeyStatus(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public KeyStatus setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getAttribute() {
		return attribute;
	}

	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		return map;
	}

}