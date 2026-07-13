package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoUsuarioJdbcMapperTest {

    @Mock
    private ResultSet resultSet;

    private final TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper = new TipoUsuarioJdbcMapper();

    @Test
    void deveMapearResultSetParaTipoUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        when(resultSet.getString("nome_tipo")).thenReturn("CLIENTE");
        when(resultSet.getObject("tipo_usuario_id", UUID.class)).thenReturn(id);

        TipoUsuario tipoUsuario = tipoUsuarioJdbcMapper.mapRow(resultSet, 1);

        assertEquals(id, tipoUsuario.getId());
        assertEquals("CLIENTE", tipoUsuario.getNomeTipo());
    }
}
