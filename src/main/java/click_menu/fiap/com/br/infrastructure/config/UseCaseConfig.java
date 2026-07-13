package click_menu.fiap.com.br.infrastructure.config;

import click_menu.fiap.com.br.application.usecases.ItensCardapios.AtualizarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.ItensCardapios.CriarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.ItensCardapios.DeletarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.AtualizarRestauranteUsecase;
import click_menu.fiap.com.br.application.usecases.restaurantes.CriarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.DeletarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.AtualizarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.BuscarTipoUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.CriarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.DeletarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.ListarTiposUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarSenhaUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase (UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        return new CriarUsuarioUseCase(usuarioRepository, passwordEncoder, usuarioMapper);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase (UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        return new DeletarUsuarioUseCase(usuarioRepository, restauranteRepository);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase (UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        return new AtualizarUsuarioUseCase(usuarioRepository, usuarioMapper);
    }

    @Bean
    public AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        return new AtualizarSenhaUsuarioUseCase(usuarioRepository, usuarioMapper, passwordEncoder);
    }

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase (RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper, UsuarioRepository usuarioRepository) {
        return new CriarRestauranteUseCase(restauranteRepository, restauranteMapper, usuarioRepository);
    }

    @Bean
    public AtualizarRestauranteUsecase atualizarRestauranteUsecase (RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper, UsuarioRepository usuarioRepository) {
        return new AtualizarRestauranteUsecase(restauranteRepository, restauranteMapper, usuarioRepository);
    }

    @Bean
    public DeletarRestauranteUseCase deletarRestauranteUseCase (RestauranteRepository restauranteRepository) {
        return new DeletarRestauranteUseCase(restauranteRepository);
    }

    @Bean
    public CriarItemCardapioUseCase criarItemCardapioUseCase (ItemCardapioRepository itemCardapioRepository, ItemCardapioMapper itemCardapioMapper, RestauranteRepository restauranteRepository) {
        return new CriarItemCardapioUseCase(itemCardapioRepository, itemCardapioMapper, restauranteRepository);
    }

    @Bean
    public AtualizarItemCardapioUseCase atualizarItemCardapioUseCase(ItemCardapioRepository itemCardapioRepository, RestauranteRepository restauranteRepository, ItemCardapioMapper itemCardapioMapper) {
        return new AtualizarItemCardapioUseCase(itemCardapioRepository, restauranteRepository, itemCardapioMapper);
    }

    @Bean
    public DeletarItemCardapioUseCase deletarItemCardapioUseCase (ItemCardapioRepository itemCardapioRepository) {
        return new DeletarItemCardapioUseCase(itemCardapioRepository);
    }

    @Bean
    public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase (TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        return new CriarTipoUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Bean
    public ListarTiposUsuarioUseCase listarTiposUsuarioUseCase (TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        return new ListarTiposUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase (TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        return new BuscarTipoUsuarioPorIdUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Bean
    public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase (TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        return new AtualizarTipoUsuarioUseCase(tipoUsuarioRepository, tipoUsuarioMapper);
    }

    @Bean
    public DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase (TipoUsuarioRepository tipoUsuarioRepository) {
        return new DeletarTipoUsuarioUseCase(tipoUsuarioRepository);
    }
}
