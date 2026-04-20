package com.fiap.ClickMenu.Services;

import com.fiap.ClickMenu.Dtos.UsuarioRequestDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Entities.Usuario;
import com.fiap.ClickMenu.Mappers.UsuarioMapper;
import com.fiap.ClickMenu.Repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::usuarioResponseDTO);
    }

    public List<UsuarioResponseDTO> buscarUsuarioPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuarioMapper::usuarioResponseDTO)
                .toList();

    }

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.usuarioResponseDTO(usuarioSalvo);

    }

    public void deletarUsuario(Long id) {
            usuarioRepository.deleteById(id);
    }

}
