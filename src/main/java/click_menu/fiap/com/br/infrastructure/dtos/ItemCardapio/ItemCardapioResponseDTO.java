package click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio;

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
