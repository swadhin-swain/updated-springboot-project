package com.swadhin.bolg.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

	public ApiException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ApiException() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	
	

}
