package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.itensCardapios.AtualizarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.CriarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.DeletarItemCardapioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item-cardapio")
public class ItemCardapioController {
    private final CriarItemCardapioUseCase criarItemCardapioUseCase;
    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    private final DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    public ItemCardapioController(CriarItemCardapioUseCase criarItemCardapioUseCase, AtualizarItemCardapioUseCase atualizarItemCardapioUseCase, DeletarItemCardapioUseCase deletarItemCardapioUseCase) {
        this.criarItemCardapioUseCase = criarItemCardapioUseCase;
        this.atualizarItemCardapioUseCase = atualizarItemCardapioUseCase;
        this.deletarItemCardapioUseCase = deletarItemCardapioUseCase;
    }

    @PostMapping
    public ResponseEntity<ItemCardapioResponseDTO> criarItemCardapio(
            @Valid @RequestBody ItemCardapioCreateDTO itemCardapioCreateDTO) {
        var itemCardapioCriado = criarItemCardapioUseCase.executar(itemCardapioCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemCardapioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapioResponseDTO> atualizar (
            @PathVariable ("id")UUID id,
            @RequestBody ItemCardapioUpdateDTO itemCardapioUpdateDTO
            ) {

        ItemCardapioResponseDTO itemCardapioAtualizado = atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO);

        return ResponseEntity.ok(itemCardapioAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar (@PathVariable ("id") UUID id) {
        deletarItemCardapioUseCase.executar(id);
    }
}
