package com.bingo.web.springbootdemo.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TradeStatusEnum {

	/**
	 * 等待支付
	 */
	WAITING_PAYMENT(0,"等待支付"),

	/**
	 * 交易成功
	 */
	SUCCESS(2,"交易成功"),

	/**
	 * 交易失败
	 */
	FAILED(3,"交易失败"),

	/**
	 * 订单已失效
	 */
	INVALID(4,"订单已失效"),

	/**
	 * 退款中
	 */
	REFUND(5,"退款中"),

	/**
	 * 退款成功
	 */
	REFUND_SUCCESS(6,"退款成功");

	/** 描述 */
	private Integer status;
	private String desc;

	private TradeStatusEnum(Integer status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static Map<String, Map<String, Object>> toMap() {
		TradeStatusEnum[] ary = TradeStatusEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			map.put("status", ary[num].getStatus());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		TradeStatusEnum[] ary = TradeStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

	public static TradeStatusEnum getEnum(String name) {
		TradeStatusEnum[] arry = TradeStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}
	
	public static TradeStatusEnum getEnumStatus(Integer status) {
		TradeStatusEnum[] arry = TradeStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].getStatus() == status) {
				return arry[i];
			}
		}
		return null;
	}

	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		TradeStatusEnum[] enums = TradeStatusEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (TradeStatusEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
