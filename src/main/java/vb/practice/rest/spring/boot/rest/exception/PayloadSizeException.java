package vb.practice.rest.spring.boot.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import vb.practice.rest.spring.boot.utils.MessageUtil;

import javax.xml.ws.WebServiceException;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE, reason = MessageUtil.LARGE_PAYLOAD)
public class PayloadSizeException extends WebServiceException {

    public PayloadSizeException(String message) {
        super(message);
    }
}
