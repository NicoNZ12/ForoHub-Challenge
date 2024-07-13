package com.aluracursos.forohub.domain.usuario;

import com.aluracursos.forohub.infra.errores.ValidacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public RespuestaUsuarioDTO guardarUsuario(RegistroUsuarioDTO usuarioDTO){
        if(repository.existsByEmail(usuarioDTO.email())){
            throw new ValidacionDeIntegridad("Ya existe un usuario asociado a ese correo.");
        }

        Usuario usuario = repository.save(new Usuario(usuarioDTO, encoder));
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.isActivo());
    }

    public Page<RespuestaUsuarioDTO> mostrarUsuarios(Pageable pageable){
        var usuarios = repository.findAll();
        return repository.findAll(pageable).map(u -> new RespuestaUsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.isActivo()));
    }

    public RespuestaUsuarioDTO actualizarUsuario(ActualizarUsuarioDTO usuarioActualizar){
        Usuario usuario = repository.getReferenceById(usuarioActualizar.id());
        boolean emailEnUso = repository.existsByEmail(usuarioActualizar.email());
        if (emailEnUso && !usuario.getEmail().equals(usuarioActualizar.email())) {
            throw new ValidacionDeIntegridad("El correo ya está asociado a otro usuario.");
        }
        usuario.actualizar(usuarioActualizar, encoder);
        return new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.isActivo());
    }

    public void eliminarUsuario(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (!usuario.isPresent()) {
            throw new ValidacionDeIntegridad("El usuario asociado al id no existe.");
        }
        Usuario usuarioEncontrado = usuario.get();
        usuarioEncontrado.setActivo(false);
        repository.save(usuarioEncontrado);
    }


    public RespuestaUsuarioDTO obtenerUsuario(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (!usuario.isPresent()) {
            throw new ValidacionDeIntegridad("El usuario asociado al id no existe.");
        }
        return new RespuestaUsuarioDTO(usuario.get().getId(), usuario.get().getNombre(), usuario.get().getEmail(), usuario.get().isActivo());
    }
}
