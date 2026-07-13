package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;

import java.util.UUID;

public class AtualizarRestauranteUsecase {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteMapper restauranteMapper;
    private final UsuarioRepository usuarioRepository;

    public AtualizarRestauranteUsecase(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper, UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public RestauranteResponseDTO executar(UUID id, RestauranteUpdateDTO restauranteUpdateDTO) {
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(id).
                orElseThrow(() -> new ResourceNotFoundException("Restaurante não econtrado."));

        boolean nomeRestauranteOuEnderecoAlterado =
                !restaurante.getNomeRestaurante().equals(restauranteUpdateDTO.nomeRestaurante())
                        || !restaurante.getEnderecoRestaurante().equals(restauranteUpdateDTO.enderecoRestaurante());

        if (nomeRestauranteOuEnderecoAlterado
                && restauranteRepository.validarNomeEEnderecoExistente(restauranteUpdateDTO.nomeRestaurante(), restauranteUpdateDTO.enderecoRestaurante())) {
            throw new BusinessException("Já existe um restaurante cadastrado com esse nome neste endereço");
        }

        Usuario usuarioInformado = usuarioRepository.buscarUsuarioPorId(restauranteUpdateDTO.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException ("Usuário não encontrado"));

        if (!usuarioInformado.getTipo().getNomeTipo().equals("DONO_RESTAURANTE")) {
            throw new BusinessException("Usuário informado não está registrado como DONO de restaurante");
        }


        restaurante.setNomeRestaurante(restauranteUpdateDTO.nomeRestaurante());
        restaurante.setEnderecoRestaurante(restauranteUpdateDTO.enderecoRestaurante());
        restaurante.setTipoCozinha(restauranteUpdateDTO.tipoCozinha());
        restaurante.setHorarioAbertura(restauranteUpdateDTO.horarioAbertura());
        restaurante.setHorarioFechamento(restauranteUpdateDTO.horarioFechamento());
        restaurante.setDiasFuncionamento(restauranteUpdateDTO.diasFuncionamento());
        restaurante.setDonoRestaurante(usuarioInformado);

        Restaurante restauranteAtualizado = restauranteRepository.atualizarRestaurante(restaurante);

        return restauranteMapper.restauranteResponseDTO(restauranteAtualizado);

    }
}
