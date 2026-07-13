package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ItemCardapioJdbc implements ItemCardapioRepository {
    private final JdbcTemplate jdbc;
    private final ItemCardapioJdbcMapper itemCardapioJdbcMapper;

    public ItemCardapioJdbc(JdbcTemplate jdbc, ItemCardapioJdbcMapper itemCardapioJdbcMapper) {
        this.jdbc = jdbc;
        this.itemCardapioJdbcMapper = itemCardapioJdbcMapper;
    }

    @Override
    public ItemCardapio salvarItemCardapio(ItemCardapio itemCardapio) {
        String sql = """
                INSERT INTO item_cardapio (id, nome, descricao, preco, consumir_apenas_restaurante, foto, restaurante_id) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        jdbc.update(sql,
                itemCardapio.getId(),
                itemCardapio.getNome(),
                itemCardapio.getDescricao(),
                itemCardapio.getPreco(),
                itemCardapio.isConsumirApenasRestaurante(),
                itemCardapio.getFoto(),
                itemCardapio.getRestaurante().getId()
        );

return itemCardapio;
    }

    @Override
    public Optional<ItemCardapio> buscarItemCardapioPorId(UUID id) {
        String sql = """
            SELECT
                i.id AS item_id,
                i.nome,
                i.descricao,
                i.preco,
                i.consumir_apenas_restaurante,
                i.foto,

                r.id AS restaurante_id,
                r.nome_restaurante,
                r.endereco_restaurante,
                r.tipo_cozinha,
                r.horario_abertura,
                r.horario_fechamento,
                r.dias_funcionamento,

                u.id AS usuario_id,
                u.nome,
                u.email,
                u.senha,
                u.data_ultima_alteracao,

                t.id AS tipo_usuario_id,
                t.nome_tipo

            FROM item_cardapio i

            INNER JOIN restaurante r
                ON r.id = i.restaurante_id

            INNER JOIN usuario u
                ON u.id = r.dono_restaurante

            INNER JOIN tipo_usuario t
                ON t.id = u.tipo_id

            WHERE i.id = ?
            """;

        return jdbc.query(sql, itemCardapioJdbcMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public ItemCardapio atualizarItemCardapio(ItemCardapio itemCardapio) {
            String sql = """
                    UPDATE item_cardapio
                    SET nome = ?,
                        descricao = ?,
                        preco = ?,
                        consumir_apenas_restaurante = ?,
                        foto = ?,
                        restaurante_id = ?
                    WHERE id = ?
            """;

            jdbc.update(
                    sql,
                    itemCardapio.getNome(),
                    itemCardapio.getDescricao(),
                    itemCardapio.getPreco(),
                    itemCardapio.isConsumirApenasRestaurante(),
                    itemCardapio.getFoto(),
                    itemCardapio.getRestaurante().getId(),
                    itemCardapio.getId()
            );

            return itemCardapio;
        }

    @Override
    public void deletarItemCardapio(UUID id) {
        String sql = "DELETE FROM item_cardapio WHERE id = ?";

        jdbc.update(sql, id);
    }
}
