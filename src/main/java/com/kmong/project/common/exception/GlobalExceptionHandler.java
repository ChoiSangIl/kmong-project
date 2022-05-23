package com.kmong.project.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BizRuntimeException.class)
	protected ResponseEntity<ErrorCode> handleBindException(BizRuntimeException e) {
		e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorCode());
	}
	
	@ExceptionHandler(InvalidJwtTokenException.class)
	protected ResponseEntity<ErrorResponse> invalidTokenException(InvalidJwtTokenException e) {
    	e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ErrorCode.AUTH_INVALID));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> ReadableException(HttpMessageNotReadableException e) {
    	e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.NOT_READABLE));
	}

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_READABLE);
		errorResponse.setCustomFieldErrors(e.getFieldErrors());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalException(final Exception e) {
    	e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.NOT_READABLE));
    }
	
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
    	e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER));
    }
}
