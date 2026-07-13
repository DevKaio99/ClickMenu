package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AtualizarTipoUsuarioUseCaseTestIT {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @Test
    void deveAtualizarTipoUsuario() {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE_TESTE"));

        UUID id = tipoUsuario.getId();

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("CLIENTE_VIP_TESTE");

        atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO);

        TipoUsuario tipoUsuarioAtualizado = tipoUsuarioRepository.buscarTipoUsuarioPorId(id).orElseThrow();

        assertEquals("CLIENTE_VIP_TESTE", tipoUsuarioAtualizado.getNomeTipo());
    }

    @Test
    void naoDeveAtualizarTipoUsuarioQuandoIdNaoEncontrado() {
        UUID id = UUID.randomUUID();

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("CLIENTE_VIP_TESTE");

        assertThrows(ResourceNotFoundException.class,
                () -> atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO));
    }

    @Test
    void naoDeveAtualizarTipoUsuarioQuandoNomeJaRegistradoParaOutroTipo() {
        tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("ADMIN_TESTE"));
        TipoUsuario tipoUsuario = tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE_TESTE"));

        TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO = new TipoUsuarioUpdateDTO("ADMIN_TESTE");

        assertThrows(BusinessException.class,
                () -> atualizarTipoUsuarioUseCase.executar(tipoUsuario.getId(), tipoUsuarioUpdateDTO));
    }
}
