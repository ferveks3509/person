package ru.job4j.person.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NullPointerException.class})
    public void handlerExceptionGlobal(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/jason");
        String err = e.getMessage() + e.getClass();
        response.getWriter().write(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                f.getField(),
                                String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue())
                        ))
                        .collect(Collectors.toList())
        );
    }
}
