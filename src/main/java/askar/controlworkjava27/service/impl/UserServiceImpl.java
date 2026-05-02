package askar.controlworkjava27.service.impl;

import askar.controlworkjava27.dto.UserDto;
import askar.controlworkjava27.model.Role;
import askar.controlworkjava27.model.User;
import askar.controlworkjava27.repository.RoleRepository;
import askar.controlworkjava27.repository.UserRepository;
import askar.controlworkjava27.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto dto) {
        log.info("Registering user: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists: " + dto.getEmail());
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setEmail(dto.getEmail().trim());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName().trim());
        user.setEnabled(true);
        user.setRoles(List.of(role));

        userRepository.save(user);
        log.info("User registered successfully: {}", dto.getEmail());
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }



}
