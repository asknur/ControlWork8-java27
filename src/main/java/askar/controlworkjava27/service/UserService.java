package askar.controlworkjava27.service;

import askar.controlworkjava27.dto.UserDto;

public interface UserService {
    void register(UserDto dto);

    UserDto getByEmail(String email);
}
