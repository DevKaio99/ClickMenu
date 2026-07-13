package click_menu.fiap.com.br.infrastructure.handlers;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.infrastructure.security.exceptions.TokenGenerationException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void deveTratarResourceNotFoundException() {
        when(request.getRequestURI()).thenReturn("/api/v1/restaurantes/123");

        ResourceNotFoundException ex = new ResourceNotFoundException("Restaurante não encontrado");

        ProblemDetail problem = globalExceptionHandler.handlerResourceNotFoud(ex, request);

        assertEquals(HttpStatus.NOT_FOUND.value(), problem.getStatus());
        assertEquals("Recurso não encontrado", problem.getTitle());
        assertEquals("Restaurante não encontrado", problem.getDetail());
        assertEquals("/api/v1/restaurantes/123", problem.getProperties().get("path"));
    }

    @Test
    void deveTratarBusinessException() {
        when(request.getRequestURI()).thenReturn("/api/v1/usuarios");

        BusinessException ex = new BusinessException("Email já cadastrado");

        ProblemDetail problem = globalExceptionHandler.handlerBusiness(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), problem.getStatus());
        assertEquals("Erro de regra de negócio", problem.getTitle());
        assertEquals("Email já cadastrado", problem.getDetail());
    }

    @Test
    void deveTratarMethodArgumentNotValidException() throws NoSuchMethodException {
        when(request.getRequestURI()).thenReturn("/api/v1/usuarios");

        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "usuarioCreateDTO");
        bindingResult.addError(new FieldError("usuarioCreateDTO", "email", "não pode estar em branco"));

        MethodParameter methodParameter = new MethodParameter(
                GlobalExceptionHandlerTest.class.getDeclaredMethod("metodoFake", String.class), 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ProblemDetail problem = globalExceptionHandler.handlerValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), problem.getStatus());
        assertEquals("Erro de validação", problem.getTitle());
        assertEquals("Um ou mais campos estão inválidos", problem.getDetail());

        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) problem.getProperties().get("errors");
        assertTrue(errors.contains("email: não pode estar em branco"));
    }

    private void metodoFake(String param) {
    }

    @Test
    void deveTratarExcecaoGenerica() {
        when(request.getRequestURI()).thenReturn("/api/v1/qualquer-coisa");

        ProblemDetail problem = globalExceptionHandler.handleGenericException(new RuntimeException("Falhou"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problem.getStatus());
        assertEquals("Erro interno do servidor", problem.getTitle());
        assertEquals("Ocorreu um erro inesperado. Tente novamente mais tarde.", problem.getDetail());
    }

    @Test
    void deveTratarTokenGenerationException() {
        when(request.getRequestURI()).thenReturn("/api/v1/login");

        TokenGenerationException ex = new TokenGenerationException("Falha ao gerar token", new RuntimeException());

        ProblemDetail problem = globalExceptionHandler.handleTokenGeneration(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problem.getStatus());
        assertEquals("Erro interno ao gerar token", problem.getTitle());
    }

    @Test
    void deveTratarNoResourceFoundException() {
        when(request.getRequestURI()).thenReturn("/api/v1/inexistente");

        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/api/v1/inexistente", "/api/v1/inexistente");

        ProblemDetail problem = globalExceptionHandler.handleNoResourceFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND.value(), problem.getStatus());
        assertEquals("Endpoint não encontrado", problem.getTitle());
        assertEquals("O endpoint informado não existe.", problem.getDetail());
    }
}
