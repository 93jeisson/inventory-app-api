package com.inventory.controller;

import com.inventory.dto.LoginRequest;
import com.inventory.dto.UsuarioRequestDTO;
import com.inventory.dto.UsuarioResponseDTO;
import com.inventory.model.Usuario;
import com.inventory.security.JwtUtil;
import com.inventory.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    // ✅ Inyección por constructor (mejor práctica)
    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!usuarioService.validarPassword(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generarToken(
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }

    // 📄 LISTAR USUARIOS
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    // 🔍 OBTENER POR ID
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UsuarioResponseDTO guardarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.crearUsuario(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id,
                              @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    // ❌ ELIMINAR USUARIO
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}