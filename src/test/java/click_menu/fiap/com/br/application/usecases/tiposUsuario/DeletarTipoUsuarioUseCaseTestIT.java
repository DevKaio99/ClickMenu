package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class DeletarTipoUsuarioUseCaseTestIT {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    @Test
    void deveDeletarTipoUsuarioComIdExistente() {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("ADMIN_TESTE"));

        UUID id = tipoUsuario.getId();

        assertTrue(tipoUsuarioRepository.buscarTipoUsuarioPorId(id).isPresent());

        deletarTipoUsuarioUseCase.executar(id);

        assertTrue(tipoUsuarioRepository.buscarTipoUsuarioPorId(id).isEmpty());
    }

    @Test
    void naoDevePermitirDeletarTipoUsuarioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class,
                () -> deletarTipoUsuarioUseCase.executar(id));
    }
}
