package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Topico")
@Table(name = "topicos")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = true)
    private Usuario autor;

    private String curso;

    public Topico(CrearTopicoDTO topicoDTO, Usuario usuario) {
        this.titulo = topicoDTO.titulo();
        this.mensaje = topicoDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = Status.ACTIVO;
        this.autor = usuario;
        this.curso = topicoDTO.curso();
    }

    public Topico(Optional topicoEncontrado) {
        this.status = Status.RESUELTO;
    }

    public void actualizar(ActualizarTopicoDTO topicoDTO) {
        if(topicoDTO.titulo() != null){
            this.titulo = topicoDTO.titulo();
        }
        if(topicoDTO.mensaje() != null){
            this.mensaje = topicoDTO.mensaje();
        }
        if(topicoDTO.curso() != null){
            this.curso = topicoDTO.curso();
        }
    }
}
