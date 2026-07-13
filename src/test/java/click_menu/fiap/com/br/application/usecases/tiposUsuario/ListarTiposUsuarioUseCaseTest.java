package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarTiposUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    @BeforeEach
    void setUp() {
        listarTiposUsuarioUseCase = new ListarTiposUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Test
    void deveListarTodosOsTiposDeUsuarioCadastrados() {
        TipoUsuario tipoUsuarioAdmin = new TipoUsuario("ADMIN");
        TipoUsuario tipoUsuarioCliente = new TipoUsuario("CLIENTE");

        TipoUsuarioResponseDTO responseAdmin = new TipoUsuarioResponseDTO(tipoUsuarioAdmin.getId(), "ADMIN");
        TipoUsuarioResponseDTO responseCliente = new TipoUsuarioResponseDTO(tipoUsuarioCliente.getId(), "CLIENTE");

        when(tipoUsuarioRepository.listarTiposUsuario()).thenReturn(List.of(tipoUsuarioAdmin, tipoUsuarioCliente));
        when(tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuarioAdmin)).thenReturn(responseAdmin);
        when(tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuarioCliente)).thenReturn(responseCliente);

        List<TipoUsuarioResponseDTO> resultado = listarTiposUsuarioUseCase.executar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(responseAdmin));
        assertTrue(resultado.contains(responseCliente));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverTiposCadastrados() {
        when(tipoUsuarioRepository.listarTiposUsuario()).thenReturn(List.of());

        List<TipoUsuarioResponseDTO> resultado = listarTiposUsuarioUseCase.executar();

        assertTrue(resultado.isEmpty());
    }
}
