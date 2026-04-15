package com.inventory.service;

import com.inventory.dto.UsuarioRequestDTO;
import com.inventory.dto.UsuarioResponseDTO;
import com.inventory.model.Rol;
import com.inventory.model.Usuario;
import com.inventory.repository.RolRepository;
import com.inventory.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ✅ Constructor limpio
    public UsuarioService(RolRepository rolRepository, UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // 📄 LISTAR USUARIOS (puedes luego pasarlo a DTO)
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // =========================
    // 🆕 CREAR USUARIO (DTO PRO)
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        // 🔐 ENCRIPTAR PASSWORD
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 🔥 ASIGNAR ROL (ANTES DEL SAVE)
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);

        // 💾 GUARDAR
        Usuario guardado = usuarioRepository.save(usuario);

        // 🔚 RETURN AL FINAL
        return new UsuarioResponseDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getEmail(),
                guardado.getRol().getNombre()
        );
    }

    // =========================
    // 🔍 BUSCAR POR ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario actualizarUsuario(Long id, Usuario datos) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(datos.getNombre());
        usuario.setEmail(datos.getEmail());

        return usuarioRepository.save(usuario);
    }

    // =========================
    // ❌ ELIMINAR
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // =========================
    // 🔐 LOGIN
    public Usuario login(String email, String password) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    // =========================
    // 🔍 BUSCAR POR EMAIL
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // =========================
    // 🔐 VALIDAR PASSWORD
    public boolean validarPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }
}
