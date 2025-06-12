package com.attendance.invoices;

import com.attendance.user.Role;
import com.attendance.user.User;
import com.attendance.user.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.attendance.user.Role.ADMIN;

import java.time.LocalDate;
import java.util.ArrayList;

class InvoicesMapperTest {
    private InvoicesMapper invoicesMapper;

    @BeforeEach
    void setUp() {
        System.out.println("###########Start of Test###############");
        invoicesMapper = new InvoicesMapper();
    }

    @AfterEach
    void tearDown() {
        System.out.println("###########End of Test###############");
    }

    @Test
    public void shouldMapInvoiceDtoToInvoice() {

        InvoiceDto dto = new InvoiceDto(
                1L,
                "John Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                2137.69,
                null
        );
        UserDto user = new UserDto(
                7,
                "Jan",
                "Doe",
                "Jan Doe",
                "xxx@yyy.zz",
                "123",
                Role.MANAGER
        );
        Invoice invoice = invoicesMapper.toInvoice(dto, user);
        Assertions.assertEquals(dto.recipient(), invoice.getRecipient());
        Assertions.assertEquals(dto.seller(), invoice.getSeller());
        Assertions.assertEquals(dto.status(), invoice.getStatus());
        Assertions.assertEquals(dto.price(), invoice.getPrice());
        Assertions.assertEquals(user.id(), invoice.getUser().getId());

    }

    @Test
    void toInvoicesResponseDto() {
        User user = new User(8, "Jim", "Doe", "Jim Doe", "zzz@yyy.xx", "456", ADMIN, new ArrayList<>(), new ArrayList<>());
        invoicesMapper = new InvoicesMapper();
        Invoice invoice = new Invoice(
                2L,
                "Jane Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                1000.52,
                user
        );
        InvoicesResponseDto dto = invoicesMapper.toInvoicesResponseDto(invoice);

        Assertions.assertEquals(invoice.getId(), dto.id());
        Assertions.assertEquals(invoice.getRecipient(), dto.recipient());
        Assertions.assertEquals(invoice.getSeller(), dto.seller());
        Assertions.assertEquals(invoice.getStatus(), dto.status());
        Assertions.assertEquals(invoice.getIssuedDate(), dto.issuedDate());
        Assertions.assertEquals(invoice.getPrice(), dto.price());
        Assertions.assertEquals(invoice.getUser().getFirstName() + " " + invoice.getUser().getLastName(), dto.fullName());
    }
}