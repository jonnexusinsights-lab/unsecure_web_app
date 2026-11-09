package cr.ac.ucr.if0009.insecurecare.service;

import cr.ac.ucr.if0009.insecurecare.dto.LoginRequest;
import cr.ac.ucr.if0009.insecurecare.dto.LoginResponse;
import cr.ac.ucr.if0009.insecurecare.model.Usuario;
import cr.ac.ucr.if0009.insecurecare.repository.UsuarioRepository;
import cr.ac.ucr.if0009.insecurecare.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    // [VULNERABILIDAD INTENCIONADA: A07:2021 - Identification and Authentication Failures]
    // Comparacion directa de contrasenas en texto plano, sin BCryptPasswordEncoder
    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales invalidas para el usuario: " + request.getUsername()));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Contrasena incorrecta para: " + request.getUsername());
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name(), usuario.getId());

        return new LoginResponse(
                token,
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getRol().name(),
                usuario.getId()
        );
    }
}

