package click_menu.fiap.com.br.domain.repositories;

import click_menu.fiap.com.br.domain.entities.Restaurante;

public interface RestauranteRepository {
    boolean validarNomeEEmailExistente(String nomeRestaurante, String enderecoRestaurante);
    Restaurante salvarRestaurante(Restaurante restaurante);
}
