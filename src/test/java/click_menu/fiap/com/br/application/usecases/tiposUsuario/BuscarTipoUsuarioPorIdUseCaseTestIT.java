package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BuscarTipoUsuarioPorIdUseCaseTestIT {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Test
    void deveBuscarTipoUsuarioQuandoIdExistente() {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("ADMIN_TESTE"));

        TipoUsuarioResponseDTO resultado = buscarTipoUsuarioPorIdUseCase.executar(tipoUsuario.getId());

        assertEquals("ADMIN_TESTE", resultado.nomeTipo());
    }

    @Test
    void naoDeveBuscarTipoUsuarioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class,
                () -> buscarTipoUsuarioPorIdUseCase.executar(id));
    }
}
