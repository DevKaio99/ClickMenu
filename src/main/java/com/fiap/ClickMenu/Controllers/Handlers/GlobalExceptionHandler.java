package com.fiap.ClickMenu.Controllers.Handlers;

import com.fiap.ClickMenu.Exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ResourceNotFound> handlerResourceNotFoudException(ResourceNotFoundException e) {
//        var status = HttpStatus.NOT_FOUND;
//        return ResponseEntity.status(status.value()).body (new ResourceNotFound(status.value(), "Resource Not Found" ,e.getMessage()));
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFound> handlerResourceNotFoudException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ResourceNotFound error = new ResourceNotFound(HttpStatus.NOT_FOUND.value(), "Resource Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessError> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        BusinessError error = new BusinessError(
                HttpStatus.BAD_REQUEST.value(),
                "Business Error",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        ValidationError error = new ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                message
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                "Ocorreu um erro inesperado"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}