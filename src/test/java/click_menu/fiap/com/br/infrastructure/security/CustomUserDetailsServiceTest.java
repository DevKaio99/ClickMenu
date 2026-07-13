package click_menu.fiap.com.br.infrastructure.security;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(usuarioRepository);
    }

    @Test
    void deveCarregarUsuarioQuandoEmailExistente() {
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("CLIENTE"));

        when(usuarioRepository.findByEmailIgnoreCase("teste@email.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("teste@email.com");

        assertEquals("teste@email.com", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void deveLancarExceptionQuandoEmailNaoCadastrado() {
        when(usuarioRepository.findByEmailIgnoreCase("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("naoexiste@email.com"));
    }
}
