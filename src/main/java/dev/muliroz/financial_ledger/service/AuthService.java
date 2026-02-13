package dev.muliroz.financial_ledger.service;

import dev.muliroz.financial_ledger.dto.LoginRequestDTO;
import dev.muliroz.financial_ledger.dto.LoginResponseDTO;
import dev.muliroz.financial_ledger.dto.RegisterRequestDTO;
import dev.muliroz.financial_ledger.exception.UserAlreadyExistsException;
import dev.muliroz.financial_ledger.model.User;
import dev.muliroz.financial_ledger.repository.UserRepository;
import dev.muliroz.financial_ledger.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = (User) auth.getPrincipal();
        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponseDTO(token);
    }

    public void register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User newUser = new User(
                request.username(),
                passwordEncoder.encode(request.password())
        );

        userRepository.save(newUser);
    }
}
