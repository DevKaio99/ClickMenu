package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ItemCardapioJdbc implements ItemCardapioRepository {
    private final JdbcTemplate jdbc;

    public ItemCardapioJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
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
        return Optional.empty();
    }

    @Override
    public ItemCardapio atualizarItemCardapio(ItemCardapio itemCardapio) {
        return null;
    }

    @Override
    public void deletarItemCardapio(UUID id) {

    }
}
