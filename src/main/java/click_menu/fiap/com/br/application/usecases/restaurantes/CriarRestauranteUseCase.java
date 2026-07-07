package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;

public class CriarRestauranteUseCase {
    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;
    private final UsuarioRepository usuarioRepository;


    public CriarRestauranteUseCase(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper, UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public RestauranteResponseDTO executar(RestauranteCreateDTO restauranteCreateDTO) {
        if(restauranteRepository.validarNomeEEnderecoExistente(restauranteCreateDTO.nomeRestaurante(), restauranteCreateDTO.enderecoRestaurante())){
            throw new BusinessException("Restaurante já cadastrado nesse endereço");
        }

        Usuario donoRestaurante = usuarioRepository.buscarUsuarioPorId(restauranteCreateDTO.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Restaurante restaurante = restauranteMapper.toEntity(restauranteCreateDTO, donoRestaurante);
        Restaurante restauranteCriado = restauranteRepository.salvarRestaurante(restaurante);


        return restauranteMapper.restauranteResponseDTO(restauranteCriado);
    }
}
