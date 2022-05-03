package kr.bizdata.core.exception;

public class BzdException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BzdException() {
		super();
	}
	
	public BzdException(String msg) {
		super(msg);
	}
	
	public BzdException(Throwable cause) {
		super(cause);
	}
	
	public BzdException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
