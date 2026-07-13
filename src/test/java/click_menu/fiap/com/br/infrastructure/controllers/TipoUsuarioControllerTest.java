package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.AtualizarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.BuscarTipoUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.CriarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.DeletarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.ListarTiposUsuarioUseCase;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.security.CustomUserDetailsService;
import click_menu.fiap.com.br.infrastructure.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoUsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    @MockitoBean
    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;
    @MockitoBean
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    @MockitoBean
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    @MockitoBean
    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveCriarTipoUsuarioQuandoDadosValidos() throws Exception {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("CLIENTE");
        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE");

        when(criarTipoUsuarioUseCase.executar(any())).thenReturn(tipoUsuarioResponseDTO);

        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoUsuarioCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeTipo").value("CLIENTE"));
    }

    @Test
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("");

        mockMvc.perform(post("/api/v1/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoUsuarioCreateDTO)))
                .andExpect(status().isBadRequest());

        verify(criarTipoUsuarioUseCase, never()).executar(any());
    }

    @Test
    void deveListarTiposUsuario() throws Exception {
        List<TipoUsuarioResponseDTO> tipos = List.of(
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "ADMIN"),
                new TipoUsuarioResponseDTO(UUID.randomUUID(), "CLIENTE"));

        when(listarTiposUsuarioUseCase.executar()).thenReturn(tipos);

        mockMvc.perform(get("/api/v1/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveBuscarTipoUsuarioPorIdQuandoExistente() throws Exception {
        UUID id = UUID.randomUUID();
        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(id, "CLIENTE");

        when(buscarTipoUsuarioPorIdUseCase.executar(id)).thenReturn(tipoUsuarioResponseDTO);

        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTipo").value("CLIENTE"));
    }

    @Test
    void deveRetornar404QuandoTipoUsuarioNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarTipoUsuarioPorIdUseCase.executar(id))
                .thenThrow(new ResourceNotFoundException("Tipo de usuário não encontrado"));

        mockMvc.perform(get("/api/v1/tipos-usuario/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarTipoUsuarioQuandoDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();
        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("CLIENTE_VIP");
        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(id, "CLIENTE_VIP");

        when(atualizarTipoUsuarioUseCase.executar(eq(id), any())).thenReturn(tipoUsuarioResponseDTO);

        mockMvc.perform(put("/api/v1/tipos-usuario/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tipoUsuarioUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTipo").value("CLIENTE_VIP"));
    }

    @Test
    void deveDeletarTipoUsuarioQuandoIdExistente() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/tipos-usuario/{id}", id))
                .andExpect(status().isOk());

        verify(deletarTipoUsuarioUseCase).executar(id);
    }
}
