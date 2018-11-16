/**
 * @Title: ResponsCodeTypeEnum.java
 * @Copyright: © 2017 我来贷
 * @Company: 深圳卫盈智信科技有限公司
 */
package com.makesailing.neo.common.response.enums;


import com.makesailing.neo.common.response.Response;

/**
 * @author jamie.li
 * @description 返回码枚举类
 */

public enum ResponsCodeTypeEnum {
	
	// 返回码
	SUCCESS("0", "请求成功"),
	SYSTEM_BUSY("100", "系统繁忙"),
	REQUEST_TIME_OUT("300", "请求超时"),
	PARAMETER_ERROR("400", "参数错误"),
	NETWORK_ERROR("404", "网络异常"),
	DATA_NOT_EXISTS("600", "数据不存在");
	/**
	 * 返回码
	 */
	private String code;
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 初始化时将当前业务的返回码和返回码对应的错误信息添加到统一返回对象中
	 */
	static {
		for (ResponsCodeTypeEnum responsCodeTypeEnum : ResponsCodeTypeEnum.values()) {
			if (Response.RESPONSE_MAP.containsKey(responsCodeTypeEnum.getCode())) {
				throw new RuntimeException("业务返回码重复，请确认！重复的业务码是：" + responsCodeTypeEnum.getCode());
			}
			Response.RESPONSE_MAP.put(responsCodeTypeEnum.getCode(), responsCodeTypeEnum.getMessage());
		}
	}

	private ResponsCodeTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}