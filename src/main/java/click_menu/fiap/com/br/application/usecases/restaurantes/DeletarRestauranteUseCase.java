package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;

import java.util.UUID;

public class DeletarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    public DeletarRestauranteUseCase(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public void executar(UUID id) {
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Restaurante não encontrado"));

        restauranteRepository.deletarRestaurante(id);
    }
}
