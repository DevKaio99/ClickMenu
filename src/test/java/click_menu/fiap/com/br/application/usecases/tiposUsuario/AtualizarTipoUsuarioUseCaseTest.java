package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @BeforeEach
    void setUp() {
        atualizarTipoUsuarioUseCase = new AtualizarTipoUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Test
    void devePermitirAtualizarTipoUsuario() {
        UUID id = UUID.randomUUID();

        TipoUsuario tipoUsuario = new TipoUsuario("CLIENTE");
        tipoUsuario.setId(id);

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("CLIENTE_VIP");

        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(id, "CLIENTE_VIP");

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.of(tipoUsuario));
        when(tipoUsuarioRepository.validarNomeTipoExistente("CLIENTE_VIP")).thenReturn(false);
        when(tipoUsuarioRepository.atualizarTipoUsuario(tipoUsuario)).thenReturn(tipoUsuario);
        when(tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuario)).thenReturn(tipoUsuarioResponseDTO);

        atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO);

        verify(tipoUsuarioRepository).atualizarTipoUsuario(tipoUsuario);
    }

    @Test
    void naoDevePermitirAtualizarTipoUsuarioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("CLIENTE_VIP");

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO));

        verify(tipoUsuarioRepository, never()).atualizarTipoUsuario(any());
    }

    @Test
    void naoDevePermitirAtualizarTipoUsuarioQuandoNomeJaRegistradoParaOutroTipo() {
        UUID id = UUID.randomUUID();

        TipoUsuario tipoUsuario = new TipoUsuario("CLIENTE");
        tipoUsuario.setId(id);

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("ADMIN");

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.of(tipoUsuario));
        when(tipoUsuarioRepository.validarNomeTipoExistente("ADMIN")).thenReturn(true);

        assertThrows(BusinessException.class, () -> atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO));

        verify(tipoUsuarioRepository, never()).atualizarTipoUsuario(any());
    }
}
