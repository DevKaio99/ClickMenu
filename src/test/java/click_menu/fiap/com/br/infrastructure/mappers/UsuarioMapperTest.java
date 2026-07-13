package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioMapperTest {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper(new TipoUsuarioMapper());

    @Test
    void deveConverterCreateDTOParaEntidade() {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente.getId());

        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDTO, tipoCliente);

        assertNotNull(usuario.getId());
        assertEquals("Teste", usuario.getNome());
        assertEquals("teste@email.com", usuario.getEmail());
        assertEquals(tipoCliente, usuario.getTipo());
    }

    @Test
    void deveConverterEntidadeParaResponseDTO() {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente);

        UsuarioResponseDTO resultado = usuarioMapper.usuarioResponseDTO(usuario);

        assertEquals(usuario.getId(), resultado.id());
        assertEquals("Teste", resultado.nome());
        assertEquals(tipoCliente.getId(), resultado.tipo().id());
        assertEquals("CLIENTE", resultado.tipo().nomeTipo());
    }
}
