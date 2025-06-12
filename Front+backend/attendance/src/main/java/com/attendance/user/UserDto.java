package com.attendance.user;

import jakarta.validation.constraints.NotEmpty;
//import org.checkerframework.common.aliasing.qual.Unique;

public record UserDto(
         Integer id,
         @NotEmpty
         String firstName,
         @NotEmpty
         String lastName,
         @NotEmpty
         String fullName,
         @NotEmpty
         String email,
         @NotEmpty
         String username,
         @NotEmpty
         Role role
) {
}
