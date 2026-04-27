package com.fiap.ClickMenu.Controllers.Handlers;

import com.fiap.ClickMenu.Exceptions.BusinessException;
import com.fiap.ClickMenu.Exceptions.ResourceNotFoundException;
import com.fiap.ClickMenu.Exceptions.TokenGenerationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handlerResourceNotFoud(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Recurso não encontrado");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;

    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Erro de regra de negócio");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Erro de validação");

        var errors = ex.getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        problem.setDetail("Um ou mais campos estão inválidos");
        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;

    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Erro interno do servidor");
        problem.setDetail("Ocorreu um erro inesperado. Tente novamente mais tarde.");

        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ProblemDetail handleTokenGeneration (
            TokenGenerationException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Erro interno ao gerar token");
        problem.setDetail("Ocorreu um erro inesperado. Tente novamente mais tarde.");

        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }
}