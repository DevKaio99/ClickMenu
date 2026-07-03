package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class RestauranteMapper {
    public Restaurante toEntity(RestauranteCreateDTO restauranteCreateDTO, Usuario donoRestaurante) {
        return new Restaurante(
                restauranteCreateDTO.nomeRestaurante(),
                restauranteCreateDTO.enderecoRestaurante(),
                restauranteCreateDTO.tipoCozinha(),
                restauranteCreateDTO.horarioAbertura(),
                restauranteCreateDTO.horarioFechamento(),
                restauranteCreateDTO.diasFuncionamento(),
                donoRestaurante);
    }

    public RestauranteResponseDTO restauranteResponseDTO (Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNomeRestaurante(),
                restaurante.getEnderecoRestaurante(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento()
        );
    }
}
