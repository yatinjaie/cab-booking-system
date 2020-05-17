package org.codejudge.sb.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoWriteException;

import lombok.extern.slf4j.Slf4j;

/**
 * @description global exception handler
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MongoWriteException.class)
    public Map<String, Object> mongoWriteException(Exception ex, HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return setStatusAndMessage(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validationException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return setStatusAndMessage(HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), response);
    }

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exception(Exception ex, HttpServletResponse response) {
        log.error(ex.getLocalizedMessage(), ex);
        return setStatusAndMessage(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), response);
    }

    private Map<String, Object> setStatusAndMessage(int status, String message,
            HttpServletResponse response) {
        Map<String, Object> reponseMap = new HashMap<>();
        response.setStatus(status);
        reponseMap.put("status", "failure");
        reponseMap.put("reason", message);
        return reponseMap;
    }
}
