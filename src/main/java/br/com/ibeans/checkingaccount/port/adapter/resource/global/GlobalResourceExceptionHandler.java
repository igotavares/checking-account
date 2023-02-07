package br.com.ibeans.checkingaccount.port.adapter.resource.global;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.ErrorData;
import br.com.ibeans.checkingaccount.port.adapter.resource.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalResourceExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorData methodException(ResourceNotFoundException cause) {
        return ErrorData.builder()
                .title(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .detail(cause.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessRuntimeException.class)
    public ErrorData methodException(BusinessRuntimeException cause) {
        return ErrorData.builder()
                .title(HttpStatus.UNPROCESSABLE_ENTITY.name())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .detail(cause.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorData methodException(MethodArgumentNotValidException cause) {
        List<ErrorData.Detail> details = cause.getBindingResult().getFieldErrors()
                                    .stream()
                                    .map(createDetail())
                .collect(Collectors.toList());
        return ErrorData.builder()
                .title(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("Tem algumas violações")
                .details(details)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    private Function<FieldError, ErrorData.Detail> createDetail() {
        return fieldError ->
            ErrorData.Detail.builder()
                    .name(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorData handleException(Exception cause) {
        return ErrorData.builder()
                .title(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(cause.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }



}
