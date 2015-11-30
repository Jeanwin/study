package com.zonekey.study.common;

import java.util.List;
import java.util.Map;

/**
 * 转码结果
 * 
 * @author admin
 * 
 */
public class TransResult {
	private List<Map<String, Object>> content;
	private String response_code;
	private String response_code_string;

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getResponse_code_string() {
		return response_code_string;
	}

	public void setResponse_code_string(String response_code_string) {
		this.response_code_string = response_code_string;
	}

	public List<Map<String, Object>> getContent() {
		return content;
	}

	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TransResult [response_code=" + response_code + ", response_code_string=" + response_code_string + "]";
	}

}
