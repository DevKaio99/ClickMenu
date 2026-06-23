package click_menu.fiap.com.br.domain;


import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {


    @Test
    @DisplayName("Deve criar um usuario valido com id gerado automaticamente")
    void deveCriarUsuarioValido() {
        Usuario usuario = new Usuario("Teste","teste@email.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE);
        assertNotNull(usuario.getId());
        assertEquals("Teste", usuario.getNome());
        assertEquals(TipoUsuario.CLIENTE, usuario.getTipo());

    }

    @Test
    @DisplayName("Não deve criar usuario quando o NOME está vazio ou em branco")
    void naoDevePermitirNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("","teste@email.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE));
    }

    @Test
    @DisplayName("Não deve criar usuario quando o EMAIL não contém @")
    void naoDevePermitirEmailInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("Teste","emailinvalido.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE));
    }

    @Test
    @DisplayName("Não deve criar usuário quando o TIPO é nulo")
    void naoDevePermitirTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario ("Teste","emailinvalido.com","123456", LocalDateTime.now(), null));
    }
}
