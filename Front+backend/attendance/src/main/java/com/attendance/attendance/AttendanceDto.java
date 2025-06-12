package com.attendance.attendance;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AttendanceDto(
        Long id,
        @NotEmpty
        String recipient,
        @NotEmpty
        String seller,
        @NotNull(message = "Status is mandatory")
        Status status,
        @NotNull(message = "Date cannot be null")
        LocalDate issuedDate,
        @NotNull(message = "Price cannot be null")
        Double price,
        Integer userId
        ) {
}
