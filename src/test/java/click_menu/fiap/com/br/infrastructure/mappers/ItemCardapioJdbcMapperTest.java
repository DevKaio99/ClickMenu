package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemCardapioJdbcMapperTest {

    @Mock
    private ResultSet resultSet;
    @Mock
    private RestauranteJdbcMapper restauranteJdbcMapper;

    private ItemCardapioJdbcMapper itemCardapioJdbcMapper;

    @BeforeEach
    void setUp() {
        itemCardapioJdbcMapper = new ItemCardapioJdbcMapper(restauranteJdbcMapper);
    }

    @Test
    void deveMapearResultSetParaItemCardapio() throws Exception {
        UUID id = UUID.randomUUID();
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante);

        when(restauranteJdbcMapper.mapRow(resultSet, 1)).thenReturn(restaurante);
        when(resultSet.getString("nome")).thenReturn("Frango a milanesa");
        when(resultSet.getString("descricao")).thenReturn("frango empanado com farinha");
        when(resultSet.getBigDecimal("preco")).thenReturn(BigDecimal.valueOf(29.90));
        when(resultSet.getBoolean("consumir_apenas_restaurante")).thenReturn(true);
        when(resultSet.getString("foto")).thenReturn("/...");
        when(resultSet.getObject("item_id", UUID.class)).thenReturn(id);

        ItemCardapio itemCardapio = itemCardapioJdbcMapper.mapRow(resultSet, 1);

        assertEquals(id, itemCardapio.getId());
        assertEquals("Frango a milanesa", itemCardapio.getNome());
        assertEquals(restaurante, itemCardapio.getRestaurante());
        assertTrue(itemCardapio.isConsumirApenasRestaurante());
    }
}
