package com.fiap.ClickMenu.Services;

import com.fiap.ClickMenu.Dtos.UsuarioRequestDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Entities.Usuario;
import com.fiap.ClickMenu.Mappers.UsuarioMapper;
import com.fiap.ClickMenu.Repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario usuarioCriado = usuarioRepository.save(usuario);
        return usuarioMapper.usuarioResponseDTO(usuarioCriado);

    }

    public UsuarioResponseDTO atualizarUsuario (Long id, UsuarioResponseDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEndereco(dto.endereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        Usuario atualizado = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioResponseDTO(atualizado);

    }

    public UsuarioResponseDTO atualizarSenha (Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(dto.senha());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario senhaAtualizada = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioResponseDTO(senhaAtualizada);

    }


    public void deletarUsuario(Long id) {
            usuarioRepository.deleteById(id);
    }

}
