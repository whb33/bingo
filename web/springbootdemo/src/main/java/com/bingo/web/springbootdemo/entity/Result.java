package com.bingo.web.springbootdemo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1298621958516700076L;
	private String code;
	private String msg;
	private T data;

}
