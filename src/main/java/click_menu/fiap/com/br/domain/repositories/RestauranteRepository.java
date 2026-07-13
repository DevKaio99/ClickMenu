package click_menu.fiap.com.br.domain.repositories;

import click_menu.fiap.com.br.domain.entities.Restaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteRepository {
    boolean validarNomeEEnderecoExistente(String nomeRestaurante, String enderecoRestaurante);
    Restaurante salvarRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes();
    Optional<Restaurante> buscarRestaurantePorId (UUID id);
    Restaurante atualizarRestaurante (Restaurante restaurante);
    void deletarRestaurante (UUID id);
    boolean existePorUsuarioId(UUID usuarioId);
}
