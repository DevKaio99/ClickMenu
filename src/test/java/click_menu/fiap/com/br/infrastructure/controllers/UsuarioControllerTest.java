package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarSenhaUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.BuscarUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.ListarUsuariosUseCase;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.security.CustomUserDetailsService;
import click_menu.fiap.com.br.infrastructure.security.TokenService;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdatePassDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @MockitoBean
    private ListarUsuariosUseCase listarUsuariosUseCase;
    @MockitoBean
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @MockitoBean
    private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @MockitoBean
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    @MockitoBean
    private AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveCriarUsuarioQuandoDadosValidos() throws Exception {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                UUID.randomUUID());

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                UUID.randomUUID(),
                "Teste",
                "teste@email.com",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(criarUsuarioUseCase.executar(any())).thenReturn(usuarioResponseDTO);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    void deveRetornar400QuandoEmailInvalido() throws Exception {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(
                "Teste",
                "emailinvalido",
                "123456",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                UUID.randomUUID());

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioCreateDTO)))
                .andExpect(status().isBadRequest());

        verify(criarUsuarioUseCase, never()).executar(any());
    }

    @Test
    void deveListarUsuarios() throws Exception {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                UUID.randomUUID(),
                "Teste",
                "teste@email.com",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(listarUsuariosUseCase.executar()).thenReturn(List.of(usuarioResponseDTO));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveBuscarUsuarioPorIdQuandoExistente() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                id,
                "Teste",
                "teste@email.com",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(buscarUsuarioPorIdUseCase.executar(id)).thenReturn(usuarioResponseDTO);

        mockMvc.perform(get("/api/v1/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarUsuarioPorIdUseCase.executar(id))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get("/api/v1/usuarios/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarUsuarioQuandoDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO("NomeNovo", "teste@email.com", UUID.randomUUID());

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                id,
                "NomeNovo",
                "teste@email.com",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(atualizarUsuarioUseCase.executar(eq(id), any())).thenReturn(usuarioResponseDTO);

        mockMvc.perform(put("/api/v1/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("NomeNovo"));
    }

    @Test
    void deveAtualizarSenhaQuandoDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();

        UsuarioUpdatePassDTO usuarioUpdatePassDTO = new UsuarioUpdatePassDTO("123456", "654321");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                id,
                "Teste",
                "teste@email.com",
                LocalDateTime.of(2026, 1, 1, 10, 0),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(atualizarSenhaUsuarioUseCase.executar(eq(id), any())).thenReturn(usuarioResponseDTO);

        mockMvc.perform(put("/api/v1/usuarios/{id}/senha", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioUpdatePassDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornar400QuandoSenhaNovaEmBranco() throws Exception {
        UUID id = UUID.randomUUID();

        UsuarioUpdatePassDTO usuarioUpdatePassDTO = new UsuarioUpdatePassDTO("123456", "");

        mockMvc.perform(put("/api/v1/usuarios/{id}/senha", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioUpdatePassDTO)))
                .andExpect(status().isBadRequest());

        verify(atualizarSenhaUsuarioUseCase, never()).executar(any(), any());
    }

    @Test
    void deveDeletarUsuarioQuandoIdExistente() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/usuarios/{id}", id))
                .andExpect(status().isOk());

        verify(deletarUsuarioUseCase).executar(id);
    }
}
