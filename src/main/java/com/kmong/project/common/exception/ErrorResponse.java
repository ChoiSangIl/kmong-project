package com.kmong.project.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("errors")
	private List<CustomFieldError> customFieldErrors;

	public ErrorResponse(ErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
	}
	

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}

	// BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
	public void setCustomFieldErrors(List<FieldError> fieldErrors) {
		customFieldErrors = new ArrayList<>();

		fieldErrors.forEach(error -> {
			customFieldErrors.add(
					new CustomFieldError(error.getCodes()[0], error.getRejectedValue(), error.getDefaultMessage()));
		});
	}

	// parameter 검증에 통과하지 못한 필드가 담긴 클래스이다.
	public static class CustomFieldError {

		private String field;
		private Object value;
		private String reason;

		public CustomFieldError(String field, Object value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public String getField() {
			return field;
		}

		public Object getValue() {
			return value;
		}

		public String getReason() {
			return reason;
		}
	}
}