package com.edumatch.api.service;

import com.edumatch.api.dto.LoginRequest;
import com.edumatch.api.dto.RegistroRequest;
import com.edumatch.api.exception.ConflictoException;
import com.edumatch.api.model.Usuario;
import com.edumatch.api.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/** Lógica de registro e inicio de sesión. */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder cifrador;

    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder cifrador) {
        this.usuarioRepository = usuarioRepository;
        this.cifrador = cifrador;
    }

    /** Registra un estudiante nuevo. Falla si el correo ya existe. */
    public Usuario registrar(RegistroRequest datos) {
        if (usuarioRepository.existsByCorreo(datos.correo())) {
            throw new ConflictoException("Ya existe un usuario con ese correo");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setCorreo(datos.correo());
        // Se guarda la contraseña cifrada, nunca en texto plano
        usuario.setContrasena(cifrador.encode(datos.contrasena()));
        return usuarioRepository.save(usuario);
    }

    /** Devuelve el usuario si el correo y la contraseña son correctos; si no, vacío. */
    public Optional<Usuario> autenticar(LoginRequest datos) {
        return usuarioRepository.findByCorreo(datos.correo())
                .filter(usuario -> cifrador.matches(datos.contrasena(), usuario.getContrasena()));
    }
}
