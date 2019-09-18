package vb.practice.rest.spring.boot.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import vb.practice.rest.spring.boot.utils.MessageUtil;

import javax.xml.ws.WebServiceException;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = MessageUtil.NO_CONTENT)
public class NoTargetFoundException extends WebServiceException {

    public NoTargetFoundException(String message) {
        super(message);
    }
}
