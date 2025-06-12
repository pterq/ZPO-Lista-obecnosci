package com.attendance.invoices;

import java.time.LocalDate;

public record InvoicesResponseDto(
        Long id,
        String recipient,
        String seller,
        Status status,
        LocalDate issuedDate,
        Double price,
        String fullName
) {
}
