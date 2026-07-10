package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemCardapioMapper {
        public ItemCardapio toEntity(ItemCardapioCreateDTO itemCardapioCreateDTO, Restaurante restaurante) {
            return new ItemCardapio(
                    itemCardapioCreateDTO.nome(),
                    itemCardapioCreateDTO.descricao(),
                    itemCardapioCreateDTO.preco(),
                    itemCardapioCreateDTO.consumirApenasRestaurante(),
                    itemCardapioCreateDTO.foto(),
                    restaurante);
        }

        public ItemCardapioResponseDTO itemCardapioResponseDTO(ItemCardapio itemCardapio) {
            return new ItemCardapioResponseDTO(
                    itemCardapio.getId(),
                    itemCardapio.getNome(),
                    itemCardapio.getDescricao(),
                    itemCardapio.getPreco(),
                    itemCardapio.isConsumirApenasRestaurante(),
                    itemCardapio.getFoto()
            );
        }

}

