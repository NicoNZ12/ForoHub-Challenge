package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.usuario.ActualizarUsuarioDTO;
import com.aluracursos.forohub.domain.usuario.RegistroUsuarioDTO;
import com.aluracursos.forohub.domain.usuario.RespuestaUsuarioDTO;
import com.aluracursos.forohub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /*
    Registrar un usuario
    POST
   */
    @PostMapping
    @Transactional
    public ResponseEntity registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO usuarioDTO, UriComponentsBuilder uriComponentsBuilder){
        var usuarioRegistrado = service.guardarUsuario(usuarioDTO);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioRegistrado.id()).toUri();
        return ResponseEntity.created(url).body(usuarioRegistrado);
    }

    /*
     Listar todos los usuarios
     GET
    */
    @GetMapping
    public ResponseEntity<Page<RespuestaUsuarioDTO>> listarUsuarios(@PageableDefault(size = 5, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(service.mostrarUsuarios(pageable));
    }

    /*
     Actualizar un usuario (id)
     PUT
    */
    @PutMapping
    @Transactional
    public ResponseEntity actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO){
        return ResponseEntity.ok(service.actualizarUsuario(actualizarUsuarioDTO));
    }

    /*
     Eliminar un usuario (id)
     DELETE
    */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){ //No elimina al usuario de la bd,sino que cambia su propiedad activo de true a false
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /*
     Obtener un usuario (id)
     GET
    */
    @GetMapping("/{id}")
    public ResponseEntity obtenerUsuario(@PathVariable Long id){
        return ResponseEntity.ok(service.obtenerUsuario(id));
    }
}
