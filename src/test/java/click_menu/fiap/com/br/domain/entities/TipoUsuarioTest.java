package click_menu.fiap.com.br.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoUsuarioTest {

    @Test
    @DisplayName("Deve criar um tipo de usuário válido com id gerado automaticamente")
    void deveCriarTipoUsuarioValido() {
        TipoUsuario tipoUsuario = new TipoUsuario("DONO_RESTAURANTE");

        assertNotNull(tipoUsuario.getId());
        assertEquals("DONO_RESTAURANTE", tipoUsuario.getNomeTipo());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o nome do tipo é nulo ou em branco")
    void naoDevePermitirNomeTipoNuloOuEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> new TipoUsuario(""));
    }
}
