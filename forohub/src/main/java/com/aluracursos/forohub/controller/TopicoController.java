package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.topico.ActualizarTopicoDTO;
import com.aluracursos.forohub.domain.topico.CrearTopicoDTO;
import com.aluracursos.forohub.domain.topico.RespuestaTopicoDTO;
import com.aluracursos.forohub.domain.topico.TopicoService;
import com.aluracursos.forohub.domain.usuario.RespuestaUsuarioDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService topicoService) {
        this.service = topicoService;
    }

    /*
     Registrar un topico
     POST
    */
    @PostMapping
    @Transactional
    public ResponseEntity crearTopico(@RequestBody @Valid CrearTopicoDTO topicoDTO, UriComponentsBuilder uriComponentsBuilder){
        var topicoCreado = service.crearTopico(topicoDTO);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoCreado.id()).toUri();
        return ResponseEntity.created(url).body(topicoCreado);
    }

    /*
     Listar todos los topicos
     GET
    */
    @GetMapping
    public ResponseEntity<Page<RespuestaTopicoDTO>> listarUsuarios(@PageableDefault(size = 5, sort = "curso") Pageable pageable){
        return ResponseEntity.ok(service.listarTopicos(pageable));
    }

    /*
     Actualizar un topico (id)
     PUT
    */
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody ActualizarTopicoDTO topicoDTO){
        return ResponseEntity.ok(service.actualizarTopico(topicoDTO));
    }

    /*
     Eliminar un topico (id)
     DELETE
    */
    @DeleteMapping("/{id}") //El topico no se elimina de la bd, sino que cambia el status de ACTIVO a RESUELTO
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        service.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    /*
     Obtener un topico (id)
     GET
    */
    @GetMapping("/{id}")
    public ResponseEntity obtenerTopico(@PathVariable Long id){
        return ResponseEntity.ok(service.obtenerTopico(id));
    }
}
