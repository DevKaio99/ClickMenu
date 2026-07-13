package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioJdbcMapperTest {

    @Mock
    private ResultSet resultSet;
    @Mock
    private TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper;

    private UsuarioJdbcMapper usuarioJdbcMapper;

    @BeforeEach
    void setUp() {
        usuarioJdbcMapper = new UsuarioJdbcMapper(tipoUsuarioJdbcMapper);
    }

    @Test
    void deveMapearResultSetParaUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        when(tipoUsuarioJdbcMapper.mapTipoUsuario(resultSet)).thenReturn(tipoCliente);
        when(resultSet.getString("nome")).thenReturn("Teste");
        when(resultSet.getString("email")).thenReturn("teste@email.com");
        when(resultSet.getString("senha")).thenReturn("123456");
        when(resultSet.getObject("data_ultima_alteracao", LocalDateTime.class)).thenReturn(dataUltimaAlteracao);
        when(resultSet.getObject("usuario_id", UUID.class)).thenReturn(id);

        Usuario usuario = usuarioJdbcMapper.mapRow(resultSet, 1);

        assertEquals(id, usuario.getId());
        assertEquals("Teste", usuario.getNome());
        assertEquals("teste@email.com", usuario.getEmail());
        assertEquals(dataUltimaAlteracao, usuario.getDataUltimaAlteracao());
        assertEquals(tipoCliente, usuario.getTipo());
    }
}
