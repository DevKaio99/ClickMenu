package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarSenhaUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.BuscarUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.ListarUsuariosUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdatePassDTO;
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
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Gerenciador de usuários")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase, ListarUsuariosUseCase listarUsuariosUseCase, BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase, AtualizarUsuarioUseCase atualizarUsuarioUseCase, AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.atualizarSenhaUsuarioUseCase = atualizarSenhaUsuarioUseCase;
    }

    @Operation(summary = "Criação de Usuário", description = "Cria um Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já cadastrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário informado não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity <UsuarioResponseDTO> criar (@Valid @RequestBody UsuarioCreateDTO dto) throws IllegalAccessException {

        var usuarioCriado = criarUsuarioUseCase.executar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCriado);
    }

    @Operation(summary = "Listagem de Usuários", description = "Lista todos os Usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(listarUsuariosUseCase.executar());
    }

    @Operation(summary = "Busca de Usuário por ID", description = "Busca um Usuário quando informado um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(buscarUsuarioPorIdUseCase.executar(id));
    }

    @Operation(summary = "Deleção de Usuário", description = "Deleta um Usuário quando informado um ID e quando esse usuário não possui Restaurante cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado"),
            @ApiResponse(responseCode = "400", description = "Usuário possui restaurante(s) cadastrado(s)",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") UUID id){
        deletarUsuarioUseCase.executar(id);
    }

    @Operation(summary = "Atualização de Usuário", description = "Altera informações básicas de um Usuário quando informado um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "E-mail informado já está em uso",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário ou tipo de usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar (
            @PathVariable ("id") UUID id,
            @RequestBody UsuarioUpdateDTO dto) {

        UsuarioResponseDTO usuarioAtualizado = atualizarUsuarioUseCase.executar(id, dto);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(summary = "Alteração de Senha", description = "Altera a senha do usuário especificado pelo ID exigindo a senha atual deste usuário e a senha nova")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha atual incorreta",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha (
            @PathVariable("id") UUID id,
            @Valid @RequestBody UsuarioUpdatePassDTO dto
    ){
        UsuarioResponseDTO senhaAtualizada = atualizarSenhaUsuarioUseCase.executar(id, dto);
        return ResponseEntity.ok(senhaAtualizada);
    }
}
