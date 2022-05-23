package com.kmong.project.common.exception;

public class BizRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private ErrorCode errorCode;

	public BizRuntimeException(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
