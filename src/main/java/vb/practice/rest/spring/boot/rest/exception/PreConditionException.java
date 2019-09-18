package vb.practice.rest.spring.boot.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import vb.practice.rest.spring.boot.utils.MessageUtil;

import javax.xml.ws.WebServiceException;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = MessageUtil.PRECONDITION_FAILED)
public class PreConditionException extends WebServiceException {

    public PreConditionException(String message) {
        super(message);
    }
}

