package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CriarTipoUsuarioUseCaseTestIT {

    @Autowired
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void deveCriarTipoUsuarioNoBancoQuandoNomeInexistente() {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("DONO_RESTAURANTE_TESTE");

        TipoUsuarioResponseDTO resultado = criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO);

        assertNotNull(resultado.id());
        assertEquals("DONO_RESTAURANTE_TESTE", resultado.nomeTipo());

        assertTrue(tipoUsuarioRepository.validarNomeTipoExistente("DONO_RESTAURANTE_TESTE"));
    }

    @Test
    void naoDeveCriarTipoUsuarioQuandoNomeJaExistente() {
        TipoUsuarioCreateDTO tipoUsuarioCreateDTO = new TipoUsuarioCreateDTO("DONO_RESTAURANTE_TESTE");

        criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO);

        assertThrows(BusinessException.class,
                () -> criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO));
    }
}
