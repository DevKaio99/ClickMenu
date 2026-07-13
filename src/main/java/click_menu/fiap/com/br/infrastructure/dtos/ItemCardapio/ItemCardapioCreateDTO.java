package click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioCreateDTO (
        @NotBlank(message = "O nome do item não pode estar em branco")
        String nome,
        @NotBlank(message = "A descrição não pode estar em branco")
        String descricao,
        @NotNull(message = "O preço não pode estar em branco")
        BigDecimal preco,
        boolean consumirApenasRestaurante,
        String foto,
        @NotNull(message = "Informe um ID de restaurante")
        UUID restauranteId
) {
}
