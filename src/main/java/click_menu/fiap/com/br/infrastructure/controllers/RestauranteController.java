package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.restaurantes.AtualizarRestauranteUsecase;
import click_menu.fiap.com.br.application.usecases.restaurantes.BuscarRestaurantePorIdUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.CriarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.DeletarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.ListarRestaurantesUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurantes")
@Tag(name = "Restaurantes", description = "Gerenciador de Restaurantes")
public class RestauranteController {
    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final ListarRestaurantesUseCase listarRestaurantesUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final AtualizarRestauranteUsecase atualizarRestauranteUsecase;
    private final DeletarRestauranteUseCase deletarRestauranteUseCase;

    public RestauranteController(CriarRestauranteUseCase criarRestauranteUseCase, ListarRestaurantesUseCase listarRestaurantesUseCase, BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase, AtualizarRestauranteUsecase atualizarRestauranteUsecase, DeletarRestauranteUseCase deletarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.atualizarRestauranteUsecase = atualizarRestauranteUsecase;
        this.deletarRestauranteUseCase = deletarRestauranteUseCase;
    }

    @Operation(summary = "Criação de Restaurante", description = "Cria um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado",
                    content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário informado como dono não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(
            @Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO) {
        var restauranteCriado = criarRestauranteUseCase.executar(restauranteCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restauranteCriado);
    }

    @Operation(summary = "Lista de Restaurante", description = "Lista todos os restaurantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada")
    })
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar() {
        return ResponseEntity.ok(listarRestaurantesUseCase.executar());
    }

    @Operation(summary = "Busca de Restaurante", description = "Busca um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                    content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(buscarRestaurantePorIdUseCase.executar(id));
    }

    @Operation(summary = "Atualização de Restaurante", description = "Atualiza um restaurante conforme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado",
                    content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Nome/endereço duplicado ou usuário informado não é DONO_RESTAURANTE",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar (
            @PathVariable ("id")UUID id,
            @RequestBody RestauranteUpdateDTO restauranteUpdateDTO
            ) {

        RestauranteResponseDTO restauranteAtualizado = atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO);

        return ResponseEntity.ok(restauranteAtualizado);

    }

    @Operation(summary = "Deleção de Restaurante", description = "Deleta um restaurante conforme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante deletado")
    })
    @DeleteMapping("/{id}")
    public void deletar (@PathVariable("id") UUID id) {
        deletarRestauranteUseCase.executar(id);
    }
}
