package yteproject.application.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseBody
    public MessageResponse handleException(MethodArgumentNotValidException exception) {
        var exc = exception.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        return new MessageResponse(exc.get(0),MessageType.ERROR);
    }
}
