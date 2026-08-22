package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.itensCardapios.AtualizarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.BuscarItemCardapioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.CriarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.DeletarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.ListarItensCardapioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item-cardapio")
@Tag(name = "Itens Cardápio", description = "Itens do Cardápio de um restaurante")
public class ItemCardapioController {
    private final CriarItemCardapioUseCase criarItemCardapioUseCase;
    private final ListarItensCardapioUseCase listarItensCardapioUseCase;
    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    private final DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    public ItemCardapioController(CriarItemCardapioUseCase criarItemCardapioUseCase, ListarItensCardapioUseCase listarItensCardapioUseCase, BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase, AtualizarItemCardapioUseCase atualizarItemCardapioUseCase, DeletarItemCardapioUseCase deletarItemCardapioUseCase) {
        this.criarItemCardapioUseCase = criarItemCardapioUseCase;
        this.listarItensCardapioUseCase = listarItensCardapioUseCase;
        this.buscarItemCardapioPorIdUseCase = buscarItemCardapioPorIdUseCase;
        this.atualizarItemCardapioUseCase = atualizarItemCardapioUseCase;
        this.deletarItemCardapioUseCase = deletarItemCardapioUseCase;
    }

    @Operation(summary = "Criação de Item", description = "Cria um item do cardápio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado",
                    content = @Content(schema = @Schema(implementation = ItemCardapioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante informado não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PreAuthorize("hasRole('DONO_RESTAURANTE, ADMIN')")
    @PostMapping
    public ResponseEntity<ItemCardapioResponseDTO> criarItemCardapio(
            @Valid @RequestBody ItemCardapioCreateDTO itemCardapioCreateDTO) {
        var itemCardapioCriado = criarItemCardapioUseCase.executar(itemCardapioCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemCardapioCriado);
    }

    @Operation(summary = "Listar Itens Cardápios", description = "Lista os itens dos cardápios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada")
    })
    @GetMapping
    public ResponseEntity<List<ItemCardapioResponseDTO>> listar() {
        return ResponseEntity.ok(listarItensCardapioUseCase.executar());
    }

    @Operation(summary = "Buscar Item", description = "Busca um item do cardápio conforme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado",
                    content = @Content(schema = @Schema(implementation = ItemCardapioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapioResponseDTO> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(buscarItemCardapioPorIdUseCase.executar(id));
    }

    @Operation(summary = "Atualização de Item", description = "Atualiza um item do cardápio conforme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado",
                    content = @Content(schema = @Schema(implementation = ItemCardapioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item ou restaurante informado não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PreAuthorize("hasRole('DONO_RESTAURANTE, ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapioResponseDTO> atualizar (
            @PathVariable ("id")UUID id,
            @RequestBody ItemCardapioUpdateDTO itemCardapioUpdateDTO
            ) {

        ItemCardapioResponseDTO itemCardapioAtualizado = atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO);

        return ResponseEntity.ok(itemCardapioAtualizado);
    }

    @Operation(summary = "Deleção de item", description = "Deleta um item do cardápio conforme ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deletado")
    })
    @PreAuthorize("hasRole('DONO_RESTAURANTE, ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar (@PathVariable ("id") UUID id) {
        deletarItemCardapioUseCase.executar(id);
    }
}
