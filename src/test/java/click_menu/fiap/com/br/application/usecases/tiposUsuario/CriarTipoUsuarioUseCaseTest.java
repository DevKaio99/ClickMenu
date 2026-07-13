package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Mock
    private TipoUsuarioMapper tipoUsuarioMapper;

    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @BeforeEach
    void setUp() {
        criarTipoUsuarioUseCase = new CriarTipoUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Test
    void deveCriarTipoUsuarioQuandoNomeNaoExistente() {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("DONO_RESTAURANTE");
        TipoUsuario tipoUsuario = new TipoUsuario("DONO_RESTAURANTE");
        TipoUsuarioResponseDTO tipoUsuarioResponseDTO = new TipoUsuarioResponseDTO(tipoUsuario.getId(), "DONO_RESTAURANTE");

        when(tipoUsuarioRepository.validarNomeTipoExistente("DONO_RESTAURANTE")).thenReturn(false);
        when(tipoUsuarioMapper.toEntity(tipoUsuarioCreateDTO)).thenReturn(tipoUsuario);
        when(tipoUsuarioRepository.salvarTipoUsuario(tipoUsuario)).thenReturn(tipoUsuario);
        when(tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuario)).thenReturn(tipoUsuarioResponseDTO);

        TipoUsuarioResponseDTO resultado = criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO);

        assertNotNull(resultado);
        assertEquals("DONO_RESTAURANTE", resultado.nomeTipo());

        verify(tipoUsuarioRepository).salvarTipoUsuario(tipoUsuario);
    }

    @Test
    void naoDeveCriarTipoUsuarioQuandoNomeJaExistente() {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("DONO_RESTAURANTE");

        when(tipoUsuarioRepository.validarNomeTipoExistente("DONO_RESTAURANTE")).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO));

        verify(tipoUsuarioRepository, never()).salvarTipoUsuario(any());
    }
}
