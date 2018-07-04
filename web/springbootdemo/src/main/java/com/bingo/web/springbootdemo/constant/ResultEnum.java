package com.bingo.web.springbootdemo.constant;

public enum ResultEnum {
	
	SUCCESS("1", "成功"),
	FAIL("0", "失败"),
	ERROR("-1", "异常"),
	ERORR_INPUT("-2", "输入的参数非法"),
	ERORR_FILE("-3", "上传的文件有问题");
	
	private String code;
    private String message;
    
    private ResultEnum(String code, String message) {
    	this.code = code;
    	this.message = message;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isSuccess(){
        return ResultEnum.SUCCESS.equals(this);
    }
	
	public boolean isUnSuccess(){
        return !isSuccess();
    }

}
