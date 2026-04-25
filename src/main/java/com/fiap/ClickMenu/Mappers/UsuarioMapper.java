package com.fiap.ClickMenu.Mappers;

import com.fiap.ClickMenu.Dtos.UsuarioCreateDTO;
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
                usuario.getEndereco(),
                usuario.getDataUltimaAlteracao(),
                usuario.getTipo()
        );
    }

    public Usuario toEntity(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setEndereco(dto.endereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        usuario.setTipo(dto.tipo());

        return usuario;
    }
}