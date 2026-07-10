package click_menu.fiap.com.br.infrastructure.dtos;

import click_menu.fiap.com.br.domain.entities.Restaurante;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean consumirApenasRestaurante,
        String foto
) {
}
