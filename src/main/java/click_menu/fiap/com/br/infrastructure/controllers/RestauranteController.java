package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.restaurantes.AtualizarRestauranteUsecase;
import click_menu.fiap.com.br.application.usecases.restaurantes.CriarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.DeletarRestauranteUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {
    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final AtualizarRestauranteUsecase atualizarRestauranteUsecase;
    private final DeletarRestauranteUseCase deletarRestauranteUseCase;

    public RestauranteController(CriarRestauranteUseCase criarRestauranteUseCase, AtualizarRestauranteUsecase atualizarRestauranteUsecase, DeletarRestauranteUseCase deletarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.atualizarRestauranteUsecase = atualizarRestauranteUsecase;
        this.deletarRestauranteUseCase = deletarRestauranteUseCase;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO) {
        var restauranteCriado = criarRestauranteUseCase.executar(restauranteCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restauranteCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar (
            @PathVariable ("id")UUID id,
            @RequestBody RestauranteUpdateDTO restauranteUpdateDTO
            ) {

        RestauranteResponseDTO restauranteAtualizado = atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO);

        return ResponseEntity.ok(restauranteAtualizado);

    }

    @DeleteMapping("/{id}")
    public void deletar (@PathVariable("id") UUID id) {
        deletarRestauranteUseCase.executar(id);
    }
}
