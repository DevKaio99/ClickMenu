package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;

import java.util.UUID;

public class AtualizarItemCardapioUseCase {
    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepository restauranteRepository;
    private final ItemCardapioMapper itemCardapioMapper;

    public AtualizarItemCardapioUseCase(ItemCardapioRepository itemCardapioRepository, RestauranteRepository restauranteRepository, ItemCardapioMapper itemCardapioMapper) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepository = restauranteRepository;
        this.itemCardapioMapper = itemCardapioMapper;
    }

    public ItemCardapioResponseDTO executar(UUID id, ItemCardapioUpdateDTO itemCardapioUpdateDTO) {
        ItemCardapio itemCardapio = itemCardapioRepository.buscarItemCardapioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(itemCardapioUpdateDTO.restaurante())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        itemCardapio.setNome(itemCardapioUpdateDTO.nome());
        itemCardapio.setDescricao(itemCardapioUpdateDTO.descricao());
        itemCardapio.setPreco(itemCardapioUpdateDTO.preco());
        itemCardapio.setConsumirApenasRestaurante(itemCardapioUpdateDTO.consumirApenasRestaurante());
        itemCardapio.setFoto(itemCardapioUpdateDTO.foto());

        ItemCardapio itemCardapioAtualizado = itemCardapioRepository.atualizarItemCardapio(itemCardapio);

        return itemCardapioMapper.itemCardapioResponseDTO(itemCardapioAtualizado);
    }
}
