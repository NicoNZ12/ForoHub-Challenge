package com.aluracursos.forohub.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    private boolean activo;

    public Usuario(RegistroUsuarioDTO usuarioDTO, PasswordEncoder encoder) {
        this.activo = true;
        this.nombre = usuarioDTO.nombre();
        this.email = usuarioDTO.email();
        this.password = encoder.encode(usuarioDTO.password());
    }

    public void actualizar(ActualizarUsuarioDTO usuarioActualizar, PasswordEncoder encoder) {
        if(usuarioActualizar.nombre() != null ){
            this.nombre = usuarioActualizar.nombre();
        }
        if(usuarioActualizar.email() != null){
            this.email = usuarioActualizar.email();
        }
        if(usuarioActualizar.password() != null){
            this.password = encoder.encode(usuarioActualizar.password());
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
