package kr.bizdata.core.exception;

public class BzdNotFoundException extends BzdException {
	
	private static final long serialVersionUID = 1L;
	
	public BzdNotFoundException(String message) {
		super(message);
	}
	
	public BzdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
