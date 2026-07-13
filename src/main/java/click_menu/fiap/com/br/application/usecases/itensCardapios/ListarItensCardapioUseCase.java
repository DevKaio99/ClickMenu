package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;

import java.util.List;

public class ListarItensCardapioUseCase {
    private final ItemCardapioRepository itemCardapioRepository;
    private final ItemCardapioMapper itemCardapioMapper;

    public ListarItensCardapioUseCase(ItemCardapioRepository itemCardapioRepository, ItemCardapioMapper itemCardapioMapper) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.itemCardapioMapper = itemCardapioMapper;
    }

    public List<ItemCardapioResponseDTO> executar() {
        return itemCardapioRepository.listarItensCardapio()
                .stream()
                .map(itemCardapioMapper::itemCardapioResponseDTO)
                .toList();
    }
}
