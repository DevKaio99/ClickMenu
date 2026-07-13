package click_menu.fiap.com.br.infrastructure.config;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class InicializacaoDados {

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            for (String nomeTipo : List.of("ADMIN", "CLIENTE", "DONO_RESTAURANTE")) {
                if (!tipoUsuarioRepository.validarNomeTipoExistente(nomeTipo)) {
                    tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario(nomeTipo));
                }
            }

            if (usuarioRepository.findByEmailIgnoreCase("admin@email.com").isEmpty()) {

                TipoUsuario tipoAdmin = tipoUsuarioRepository.listarTiposUsuario().stream()
                        .filter(tipo -> tipo.getNomeTipo().equals("ADMIN"))
                        .findFirst()
                        .orElseThrow();

                Usuario usuarioInicial = new Usuario(
                "Admin",
                "admin@email.com",
                passwordEncoder.encode("fiap123"),
                LocalDateTime.now(),
                tipoAdmin
                );

                usuarioRepository.salvarUsuario(usuarioInicial);

            }
        };
    }
}
