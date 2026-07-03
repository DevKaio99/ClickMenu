package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.restaurantes.CriarRestauranteUseCase;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {
    private final CriarRestauranteUseCase criarRestauranteUseCase;

    public RestauranteController(CriarRestauranteUseCase criarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO) {
        var restauranteCriado = criarRestauranteUseCase.executar(restauranteCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restauranteCriado);
    }
}
