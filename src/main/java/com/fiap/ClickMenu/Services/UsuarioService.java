package com.fiap.ClickMenu.Services;

import com.fiap.ClickMenu.Dtos.UsuarioCreateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdatePassDTO;
import com.fiap.ClickMenu.Entities.Usuario;
import com.fiap.ClickMenu.Mappers.UsuarioMapper;
import com.fiap.ClickMenu.Repositories.UsuarioRepository;
import com.fiap.ClickMenu.Exceptions.BusinessException;
import com.fiap.ClickMenu.Exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {

        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario usuarioCriado = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioResponseDTO(usuarioCriado);

    }

    public UsuarioResponseDTO atualizarUsuario (Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEndereco(dto.endereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        Usuario atualizado = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioResponseDTO(atualizado);

    }

    public UsuarioResponseDTO atualizarSenha (Long id, UsuarioUpdatePassDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(dto.senhaAtual())) {
            throw new BusinessException("Senha atual incorreta");
        }
        usuario.setSenha(dto.senhaNova());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario senhaAtualizada = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioResponseDTO(senhaAtualizada);

    }


    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);

    }

}
