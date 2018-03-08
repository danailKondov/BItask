package ru.bellintegrator.practice.registration.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bellintegrator.practice.registration.exceptions.AccountException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 08.03.2018.
 */
@ControllerAdvice
public class AccountExceptionsHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(AccountExceptionsHandler.class);

    /**
     * Обрабатывает исключение AccountException.
     * @param e исключение
     * @return объект-обертку (Map) с сообщением об ошибке
     */
    @ExceptionHandler({/*NestedServletException.class,*/ AccountException.class})
    protected @ResponseBody ResponseEntity<Map> handleAccountExceptions(AccountException e) {
        log.error(e.getMessage(), e.getCause());
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity<Map>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает все прочие исключения, не обработанные ранее.
     * @param e исключение
     * @return объект-обертку (Map) с сообщением об ошибке
     */
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Map> handleAllExceptions(Exception e) {
        log.error(e.getMessage(), e);
        Map<String, String> result = new HashMap<>();
        result.put("error", "Internal Server Error");
        return new ResponseEntity<Map>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
