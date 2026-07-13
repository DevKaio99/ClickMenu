package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;

public class CriarItemCardapioUseCase {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ItemCardapioMapper itemCardapioMapper;
    private final RestauranteRepository restauranteRepository;


    public CriarItemCardapioUseCase(ItemCardapioRepository itemCardapioRepository, ItemCardapioMapper itemCardapioMapper, RestauranteRepository restauranteRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.itemCardapioMapper = itemCardapioMapper;
        this.restauranteRepository = restauranteRepository;
    }

    public ItemCardapioResponseDTO executar(ItemCardapioCreateDTO itemCardapioCreateDTO) {
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(itemCardapioCreateDTO.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restauurante não encontrado"));

        ItemCardapio itemCardapio = itemCardapioMapper.toEntity(itemCardapioCreateDTO, restaurante);
        ItemCardapio itemCardapioCriado = itemCardapioRepository.salvarItemCardapio(itemCardapio);

        return itemCardapioMapper.itemCardapioResponseDTO(itemCardapioCriado);
    }
}
