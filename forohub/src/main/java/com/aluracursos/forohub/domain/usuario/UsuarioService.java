package com.aluracursos.forohub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public RespuestaUsuarioDTO guardarUsuario(RegistroUsuarioDTO usuarioDTO){
        if(repository.findByEmail(usuarioDTO.email()).isPresent()){
            throw new RuntimeException("Ya existe un usuario asociado a ese correo.");
        }

        Usuario usuario = repository.save(new Usuario(usuarioDTO));
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

    public Page<RespuestaUsuarioDTO> mostrarUsuarios(Pageable pageable){
        var usuarios = repository.findAll();
        return repository.findAll(pageable).map(u -> new RespuestaUsuarioDTO(u.getId(), u.getNombre(), u.getEmail()));
    }

    public RespuestaUsuarioDTO actualizarUsuario(ActualizarUsuarioDTO usuarioActualizar){
        Usuario usuario = repository.getReferenceById(usuarioActualizar.id());
        if(usuario.getEmail().equals(repository.findByEmail(usuarioActualizar.email()))){
            throw new RuntimeException("El correo ya está asociado a otro usuario.");
        }
        usuario.actualizar(usuarioActualizar);
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = repository.getReferenceById(id);
        if(!usuario.getId().equals(repository.findById(id))){
            throw new RuntimeException("El usuario asocidado al id no existe");
        }
        repository.delete(usuario);
    }

    public RespuestaUsuarioDTO obtenerUsuario(Long id) {
        Usuario usuario = repository.getReferenceById(id);
        if(!usuario.getId().equals(repository.findById(id))){
            throw new RuntimeException("El usuario asocidado al id no existe");
        }
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
