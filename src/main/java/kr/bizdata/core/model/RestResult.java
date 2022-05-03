package kr.bizdata.core.model;

import org.slf4j.LoggerFactory;

public class RestResult {
	
	private boolean result; // 결과 (true: 성공, false: 실패)
	private String msg; // 메시지 (실패시에만)
	private Object data;
	
	public RestResult(boolean result) {
		this.result = result;
	}
	
	public RestResult(Throwable cause) {
		this.result = false;
		if (cause != null) {
			LoggerFactory.getLogger(getClass()).error(cause.getMessage());
			this.msg = cause.getMessage();
		}
	}
	
	public RestResult(Object data) {
		this.result = true;
		this.data = data;
	}
	
	public RestResult(boolean result, String msg, Object data) {
		this.result = result;
		this.msg = msg;
		this.data = data;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public Object getData() {
		return data;
	}
	
}
