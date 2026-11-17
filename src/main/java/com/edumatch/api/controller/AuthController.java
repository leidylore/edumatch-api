package com.edumatch.api.controller;

import com.edumatch.api.dto.LoginRequest;
import com.edumatch.api.dto.RegistroRequest;
import com.edumatch.api.model.Usuario;
import com.edumatch.api.service.AuthService;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Servicios de registro e inicio de sesión. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** POST /api/auth/registro -> crea un estudiante (201). */
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody RegistroRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(datos));
    }

    /** POST /api/auth/login -> 200 si las credenciales son correctas, 401 si no. */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> iniciarSesion(@Valid @RequestBody LoginRequest datos) {
        Optional<Usuario> usuario = authService.autenticar(datos);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Autenticación satisfactoria",
                    "usuarioId", usuario.get().getId(),
                    "nombre", usuario.get().getNombre()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Error en la autenticación"));
    }
}
