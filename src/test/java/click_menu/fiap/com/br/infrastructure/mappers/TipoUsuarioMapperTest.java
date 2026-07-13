package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoUsuarioMapperTest {

    private final TipoUsuarioMapper tipoUsuarioMapper = new TipoUsuarioMapper();

    @Test
    void deveConverterCreateDTOParaEntidade() {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("CLIENTE");

        TipoUsuario tipoUsuario = tipoUsuarioMapper.toEntity(tipoUsuarioCreateDTO);

        assertNotNull(tipoUsuario.getId());
        assertEquals("CLIENTE", tipoUsuario.getNomeTipo());
    }

    @Test
    void deveConverterEntidadeParaResponseDTO() {
        TipoUsuario tipoUsuario = new TipoUsuario("CLIENTE");

        TipoUsuarioResponseDTO resultado = tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuario);

        assertEquals(tipoUsuario.getId(), resultado.id());
        assertEquals("CLIENTE", resultado.nomeTipo());
    }
}
