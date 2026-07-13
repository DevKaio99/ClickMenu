package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;

import java.util.List;

public class ListarRestaurantesUseCase {
    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;

    public ListarRestaurantesUseCase(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
    }

    public List<RestauranteResponseDTO> executar() {
        return restauranteRepository.listarRestaurantes()
                .stream()
                .map(restauranteMapper::restauranteResponseDTO)
                .toList();
    }
}
