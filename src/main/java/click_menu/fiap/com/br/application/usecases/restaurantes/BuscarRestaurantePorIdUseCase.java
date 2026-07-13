package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;

import java.util.UUID;

public class BuscarRestaurantePorIdUseCase {
    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;

    public BuscarRestaurantePorIdUseCase(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
    }

    public RestauranteResponseDTO executar(UUID id) {
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        return restauranteMapper.restauranteResponseDTO(restaurante);
    }
}
