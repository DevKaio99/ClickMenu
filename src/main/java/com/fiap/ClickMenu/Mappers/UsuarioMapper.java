package com.fiap.ClickMenu.Mappers;

import com.fiap.ClickMenu.Dtos.UsuarioRequestDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Entities.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsuarioMapper {
    public UsuarioResponseDTO usuarioResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getEndereco(),
                usuario.getDataUltimaAlteracao(),
                usuario.getTipo()
        );
    }

    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setLogin(dto.login());
        usuario.setSenha(dto.senha());
        usuario.setEndereco(dto.endereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        usuario.setTipo(dto.tipo());

        return usuario;
    }
}