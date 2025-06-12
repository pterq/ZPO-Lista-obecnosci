package com.attendance.student;

import jakarta.validation.constraints.NotEmpty;
//import org.checkerframework.common.aliasing.qual.Unique;

public record StudentDto(
         Integer id,
         @NotEmpty
         String firstName,
         @NotEmpty
         String lastName,
         @NotEmpty
         String fullName,
         @NotEmpty
         String username,
         @NotEmpty
         Role role
) {
}
