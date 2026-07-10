package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.ItensCardapios.CriarItemCardapioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item-cardapio")
public class ItemCardapioController {
    private final CriarItemCardapioUseCase criarItemCardapioUseCase;

    public ItemCardapioController(CriarItemCardapioUseCase criarItemCardapioUseCase) {
        this.criarItemCardapioUseCase = criarItemCardapioUseCase;
    }

    @PostMapping
    public ResponseEntity<ItemCardapioResponseDTO> criarItemCardapio(
            @Valid @RequestBody ItemCardapioCreateDTO itemCardapioCreateDTO) {
        var itemCardapioCriado = criarItemCardapioUseCase.executar(itemCardapioCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemCardapioCriado);
    }
}
