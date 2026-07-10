package click_menu.fiap.com.br.domain.repositories;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;

import java.util.Optional;
import java.util.UUID;


public interface ItemCardapioRepository {
    ItemCardapio salvarItemCardapio(ItemCardapio itemCardapio);
    Optional<ItemCardapio> buscarItemCardapioPorId (UUID id);
    ItemCardapio atualizarItemCardapio (ItemCardapio itemCardapio);
    void deletarItemCardapio (UUID id);
}
