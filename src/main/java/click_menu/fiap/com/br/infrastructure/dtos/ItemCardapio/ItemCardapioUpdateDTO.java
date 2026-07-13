package click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioUpdateDTO (
        String nome,
        String descricao,
        BigDecimal preco,
        boolean consumirApenasRestaurante,
        String foto,
        @NotNull(message = "Informe o ID de um restaurante")
        UUID restaurante
) {

}

