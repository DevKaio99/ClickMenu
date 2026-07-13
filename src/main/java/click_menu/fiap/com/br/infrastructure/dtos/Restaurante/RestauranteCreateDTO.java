package click_menu.fiap.com.br.infrastructure.dtos.Restaurante;

import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record RestauranteCreateDTO(
        @NotBlank(message = "O nome do restaurante não pode estar em branco")
        String nomeRestaurante,
        @NotBlank(message = "O endereço não pode estar em branco")
        String enderecoRestaurante,
        @NotNull(message = "Informe o tipo de cozinha")
        TipoCozinhaRestaurante tipoCozinha,
        @NotNull(message = "Informe o horário de funcionamento")
        LocalTime horarioAbertura,
        @NotNull(message = "Informe o horário de funcionamento")
        LocalTime horarioFechamento,
        @NotNull(message = "Informe os dias de funcionamento")
        Set<DiasDaSemana> diasFuncionamento,
        @NotNull(message = "Informe o ID de um usuário que está registrado como Dono de Restaurante")
        UUID usuarioId) {
}
