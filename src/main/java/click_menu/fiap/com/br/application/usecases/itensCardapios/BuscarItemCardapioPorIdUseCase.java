package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;

import java.util.UUID;

public class BuscarItemCardapioPorIdUseCase {
    private final ItemCardapioRepository itemCardapioRepository;
    private final ItemCardapioMapper itemCardapioMapper;

    public BuscarItemCardapioPorIdUseCase(ItemCardapioRepository itemCardapioRepository, ItemCardapioMapper itemCardapioMapper) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.itemCardapioMapper = itemCardapioMapper;
    }

    public ItemCardapioResponseDTO executar(UUID id) {
        ItemCardapio itemCardapio = itemCardapioRepository.buscarItemCardapioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado"));

        return itemCardapioMapper.itemCardapioResponseDTO(itemCardapio);
    }
}
