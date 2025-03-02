
package com.goktug.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private Integer statusCode;

    public BaseException(ErrorMessage errorMessage,Integer statusCode) {
        super(errorMessage.prepareErrorMessage());
        
        this.statusCode = statusCode;
    }

}