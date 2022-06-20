package com.example.lmclients.controller;

import com.example.lmclients.domain.enums.TypeErrors;
import com.example.lmclients.exception.ApiErrors;
import com.example.lmclients.exception.ClientInexistenteException;
import com.example.lmclients.exception.Field;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ApplicationControllerAdvice (MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ClientInexistenteException.class)
    public ResponseEntity<Object> handlerClientInexistente(ClientInexistenteException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TypeErrors typeErrors = TypeErrors.CLIENT_INEXISTENT;
        String detail = ex.getMessage();

        ApiErrors apiErrors = getApiErrorsBuilders(status, typeErrors, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<Field>fieldList = bindingResult.getFieldErrors().stream().map(
                fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    String name = null;

                    if (fieldError instanceof FieldError) {
                        name = ((FieldError) fieldError).getField();
                    }

                    return Field.builder()
                            .userMessage(message)
                            .name(name)
                            .build();
                }).toList();

        TypeErrors typeErrors = TypeErrors.DADOS_INVALIDOS;
        String detail =  "Um ou mais dados informados são inválidos. Corrija e informe dados válidos.";

        ApiErrors apiErrors = getApiErrorsBuilders(status, typeErrors, detail).fieldsList(fieldList).build();

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
