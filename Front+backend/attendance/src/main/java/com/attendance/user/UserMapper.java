package com.attendance.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDto toUserDto(User user)
    {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFirstName()+" "+user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole()
        );

    }
}
