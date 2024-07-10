package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.usuario.RespuestaUsuarioDTO;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import com.aluracursos.forohub.infra.errores.ValidacionDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    private final TopicoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public TopicoService(TopicoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }


    public RespuestaTopicoDTO crearTopico(CrearTopicoDTO topicoDTO){
        Usuario usuario = usuarioRepository.findById(topicoDTO.id_autor()).get();
        if (repository.existsByTituloIgnoreCase(topicoDTO.titulo()) || repository.existsByMensajeIgnoreCase(topicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("El título o mensaje para el tópico ya existe en la base de datos.");
        }
        var topico = repository.save(new Topico(topicoDTO, usuario));
        return new RespuestaTopicoDTO(topico);
    }

    public Page<RespuestaTopicoDTO> listarTopicos(Pageable pageable) {
        var topicos = repository.findAll();
        return repository.findAll(pageable).map(t -> new RespuestaTopicoDTO(t.getId(),t.getTitulo(), t.getMensaje(), t.getFechaCreacion(), t.getStatus(),t.getAutor().getId(), t.getCurso()));
    }

    public RespuestaTopicoDTO obtenerTopico(Long id) {
        var topicoEncontrado = repository.findById(id);
        if(!topicoEncontrado.isPresent()){
            throw new ValidacionDeIntegridad("No se encontró el tópico en la base de datos.");
        }
        return new RespuestaTopicoDTO(topicoEncontrado.get());
    }

    public void eliminarTopico(Long id) {
        var topicoEncontrado = repository.findById(id);
        if(!topicoEncontrado.isPresent()){
            throw new ValidacionDeIntegridad("No se encontró el tópico en la base de datos.");
        }

        Topico topico = topicoEncontrado.get();
        if (topico.getStatus() == Status.RESUELTO) {
            throw new ValidacionDeIntegridad("El tópico ya se encuentra resuelto.");
        }

        topico.setStatus(Status.RESUELTO);
        repository.save(topico);
    }

    public RespuestaTopicoDTO actualizarTopico(ActualizarTopicoDTO topicoDTO) {
        Topico topico = repository.getReferenceById(topicoDTO.id());
        if (repository.existsByTituloIgnoreCase(topicoDTO.titulo()) || repository.existsByMensajeIgnoreCase(topicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("El título o mensaje para el tópico ya existe en la base de datos.");
        }
        topico.actualizar(topicoDTO);
        return new RespuestaTopicoDTO(topico);
    }
}
