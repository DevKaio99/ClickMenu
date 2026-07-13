package click_menu.fiap.com.br.domain.entities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {


    @Test
    @DisplayName("Deve criar um usuario valido com id gerado automaticamente")
    void deveCriarUsuarioValido() {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        Usuario usuario = new Usuario("Teste","teste@email.com","123456", LocalDateTime.now(), tipoCliente);
        assertNotNull(usuario.getId());
        assertEquals("Teste", usuario.getNome());
        assertEquals(tipoCliente, usuario.getTipo());

    }

    @Test
    @DisplayName("Não deve criar usuario quando o NOME está vazio ou em branco")
    void naoDevePermitirNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("","teste@email.com","123456", LocalDateTime.now(), new TipoUsuario("CLIENTE")));
    }

    @Test
    @DisplayName("Não deve criar usuario quando o EMAIL não contém @")
    void naoDevePermitirEmailInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("Teste","emailinvalido.com","123456", LocalDateTime.now(), new TipoUsuario("CLIENTE")));
    }

    @Test
    @DisplayName("Não deve criar usuário quando o TIPO é nulo")
    void naoDevePermitirTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario ("Teste","emailinvalido.com","123456", LocalDateTime.now(), null));
    }

    @Test
    @DisplayName("Não deve permitir criar usuário com senha menor que 6 caracteres")
    void naoDevePermitirSenhaCurta() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario ("Teste","emailinvalido.com","123", LocalDateTime.now(), new TipoUsuario("CLIENTE")));
    }
}
