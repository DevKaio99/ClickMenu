package click_menu.fiap.com.br.application.usecases.ItensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;

import java.util.UUID;

public class DeletarItemCardapioUseCase {
    private final ItemCardapioRepository itemCardapioRepository;


    public DeletarItemCardapioUseCase(ItemCardapioRepository itemCardapioRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
    }

    public void executar(UUID id) {
        ItemCardapio itemCardapio = itemCardapioRepository.buscarItemCardapioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        itemCardapioRepository.deletarItemCardapio(id);

    }
}
