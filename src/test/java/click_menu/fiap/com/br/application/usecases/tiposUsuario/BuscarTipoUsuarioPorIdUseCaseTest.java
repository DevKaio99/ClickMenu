package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuscarTipoUsuarioPorIdUseCaseTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @BeforeEach
    void setUp() {
        buscarTipoUsuarioPorIdUseCase = new BuscarTipoUsuarioPorIdUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Test
    void deveBuscarTipoUsuarioQuandoIdExistente() {
        UUID id = UUID.randomUUID();

        TipoUsuario tipoUsuario = new TipoUsuario("ADMIN");
        tipoUsuario.setId(id);

        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(id, "ADMIN");

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.of(tipoUsuario));
        when(tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuario)).thenReturn(tipoUsuarioResponseDTO);

        TipoUsuarioResponseDTO resultado = buscarTipoUsuarioPorIdUseCase.executar(id);

        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.nomeTipo());
    }

    @Test
    void naoDeveBuscarTipoUsuarioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buscarTipoUsuarioPorIdUseCase.executar(id));
    }
}
