package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ItemCardapioJdbcMapper implements RowMapper<ItemCardapio> {

    private final RestauranteJdbcMapper restauranteJdbcMapper;

    public ItemCardapioJdbcMapper(RestauranteJdbcMapper restauranteJdbcMapper) {
        this.restauranteJdbcMapper = restauranteJdbcMapper;
    }

    @Override
    public ItemCardapio mapRow(ResultSet rs, int rowNum) throws SQLException {

        Restaurante restaurante = restauranteJdbcMapper.mapRow(rs, rowNum);

        ItemCardapio itemCardapio = new ItemCardapio(
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getBigDecimal("preco"),
                rs.getBoolean("consumir_apenas_restaurante"),
                rs.getString("foto"),
                restaurante
        );

        itemCardapio.setId(rs.getObject("item_id", UUID.class));

        return itemCardapio;
    }
}