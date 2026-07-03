package click_menu.fiap.com.br.infrastructure.dtos;

import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record RestauranteResponseDTO (
        UUID id,
        String nomeRestaurante,
        String enderecoRestaurante,
        TipoCozinhaRestaurante tipoCozinha,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        Set<DiasDaSemana> diasFuncionamento) {

}
