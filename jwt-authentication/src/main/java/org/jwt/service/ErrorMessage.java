package org.jwt.service;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorMessage {
	private int status;
	private int errorCode;
	private String message;
	private String headerName;
	private String headerValue;
}
