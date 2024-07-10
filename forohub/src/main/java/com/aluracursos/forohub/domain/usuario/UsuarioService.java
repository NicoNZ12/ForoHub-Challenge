package com.aluracursos.forohub.domain.usuario;

import com.aluracursos.forohub.infra.errores.ValidacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public RespuestaUsuarioDTO guardarUsuario(RegistroUsuarioDTO usuarioDTO){
        if(repository.findByEmail(usuarioDTO.email()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un usuario asociado a ese correo.");
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
            throw new ValidacionDeIntegridad("El correo ya está asociado a otro usuario.");
        }
        usuario.actualizar(usuarioActualizar);
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

    public void eliminarUsuario(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (!usuario.isPresent()) {
            throw new ValidacionDeIntegridad("El usuario asociado al id no existe.");
        }
        repository.delete(usuario.get());
    }


    public RespuestaUsuarioDTO obtenerUsuario(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (!usuario.isPresent()) {
            throw new ValidacionDeIntegridad("El usuario asociado al id no existe.");
        }
        return new RespuestaUsuarioDTO(usuario.get().getId(), usuario.get().getNombre(), usuario.get().getEmail());
    }
}
