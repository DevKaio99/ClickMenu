package click_menu.fiap.com.br.infrastructure.security;

import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityFilterTest {

    @Mock
    private TokenService tokenService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    private SecurityFilter securityFilter;

    @BeforeEach
    void setUp() {
        securityFilter = new SecurityFilter(tokenService, usuarioRepository, customUserDetailsService);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveAutenticarQuandoTokenValidoNoHeader() throws Exception {
        UserDetails userDetails = User.builder()
                .username("teste@email.com")
                .password("123456")
                .authorities(List.of())
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer token-valido");
        when(tokenService.validarToken("token-valido")).thenReturn("teste@email.com");
        when(customUserDetailsService.loadUserByUsername("teste@email.com")).thenReturn(userDetails);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void naoDeveAutenticarQuandoSemHeaderDeAutorizacao() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(tokenService, never()).validarToken(any());
    }
}
