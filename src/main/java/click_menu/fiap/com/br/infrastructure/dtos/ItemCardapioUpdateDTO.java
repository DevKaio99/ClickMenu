package click_menu.fiap.com.br.infrastructure.dtos;

import click_menu.fiap.com.br.domain.entities.Restaurante;

import java.math.BigDecimal;

public record ItemCardapioUpdateDTO (
        String nome,
        String descricao,
        BigDecimal preco,
        boolean consumirApenasRestaurante,
        String foto,
        Restaurante restaurante
) {

}

