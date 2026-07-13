package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestauranteJdbcMapperTest {

    @Mock
    private ResultSet resultSet;
    @Mock
    private UsuarioJdbcMapper usuarioJdbcMapper;

    private RestauranteJdbcMapper restauranteJdbcMapper;

    @BeforeEach
    void setUp() {
        restauranteJdbcMapper = new RestauranteJdbcMapper(usuarioJdbcMapper);
    }

    @Test
    void deveMapearResultSetParaRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        LocalTime horario = LocalTime.now();
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        when(resultSet.getString("dias_funcionamento")).thenReturn("SEGUNDA,TERCA");
        when(usuarioJdbcMapper.mapUsuario(resultSet)).thenReturn(usuarioDonoRestaurante);
        when(resultSet.getString("nome_restaurante")).thenReturn("RestauranteTeste");
        when(resultSet.getString("endereco_restaurante")).thenReturn("Rua de exemplo, 344");
        when(resultSet.getString("tipo_cozinha")).thenReturn("JAPONESA");
        when(resultSet.getObject("horario_abertura", LocalTime.class)).thenReturn(horario);
        when(resultSet.getObject("horario_fechamento", LocalTime.class)).thenReturn(horario);
        when(resultSet.getObject("restaurante_id", UUID.class)).thenReturn(id);

        Restaurante restaurante = restauranteJdbcMapper.mapRow(resultSet, 1);

        assertEquals(id, restaurante.getId());
        assertEquals("RestauranteTeste", restaurante.getNomeRestaurante());
        assertEquals(TipoCozinhaRestaurante.JAPONESA, restaurante.getTipoCozinha());
        assertEquals(Set.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA), restaurante.getDiasFuncionamento());
        assertEquals(usuarioDonoRestaurante, restaurante.getDonoRestaurante());
    }
}
