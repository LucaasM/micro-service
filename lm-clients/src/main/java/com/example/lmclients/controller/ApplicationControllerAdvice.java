package com.example.lmclients.controller;

import com.example.lmclients.domain.enums.TypeErrors;
import com.example.lmclients.exception.ApiErrors;
import com.example.lmclients.exception.ClientInexistenteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientInexistenteException.class)
    public ResponseEntity<Object> handlerClientInexistente(ClientInexistenteException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TypeErrors typeErrors = TypeErrors.CLIENT_INEXISTENT;
        String detail = ex.getMessage();

        ApiErrors apiErrors = getApiErrorsBuilders(status, typeErrors, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if(body == null) {
            body = ApiErrors.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if(body instanceof String){
            body = ApiErrors.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ApiErrors.ApiErrorsBuilder getApiErrorsBuilders(HttpStatus status, TypeErrors typeErrors, String detail) {
        return ApiErrors.builder()
                .status(status.value())
                .type(typeErrors.getUri())
                .title(typeErrors.getTitle())
                .detail(detail);
    }
}
