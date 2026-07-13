package click_menu.fiap.com.br.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    private final TokenService tokenService = new TokenService();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", "chave-secreta-de-teste");
    }

    @Test
    void deveGerarTokenValido() {
        UserDetails userDetails = User.builder()
                .username("teste@email.com")
                .password("123456")
                .authorities(List.of())
                .build();

        String token = tokenService.gerarToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void deveValidarTokenGeradoPorEleMesmoERetornarOSubject() {
        UserDetails userDetails = User.builder()
                .username("teste@email.com")
                .password("123456")
                .authorities(List.of())
                .build();

        String token = tokenService.gerarToken(userDetails);

        String subject = tokenService.validarToken(token);

        assertEquals("teste@email.com", subject);
    }

    @Test
    void deveRetornarVazioQuandoTokenInvalido() {
        String subject = tokenService.validarToken("token-invalido");

        assertEquals("", subject);
    }
}
