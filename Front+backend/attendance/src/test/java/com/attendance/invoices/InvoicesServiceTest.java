package com.attendance.invoices;

import com.attendance.user.Role;
import com.attendance.user.User;
import com.attendance.user.UserDto;
import com.attendance.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.attendance.user.Role.ADMIN;

class InvoicesServiceTest {
    @InjectMocks
    private InvoicesService invoicesService;
    @Mock
    private InvoicesRepository invoicesRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private InvoicesMapper invoicesMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldSuccesffulySaveAInvoice()
    {
        InvoiceDto dto = new InvoiceDto(
                1L,
                "John Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                2137.69,
                null
        );
        User user = new User
                (8,
                        "Jim",
                        "Doe",
                        "Jim Doe",
                        "zzz@yyy.xx",
                        "456",
                        ADMIN, new ArrayList<>(),
                        new ArrayList<>()
                );
        UserDto userDto = new UserDto(
                7,
                "Jan",
                "Doe",
                "Jan Doe",
                "xxx@yyy.zz",
                "123",
                Role.MANAGER
        );
        Invoice invoice = new Invoice(
                2L,
                "Jane Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                1000.52,
                user
        );
        Mockito.when(userMapper.toUserDto(user)).thenReturn(userDto);
        Mockito.when(invoicesMapper.toInvoice(dto,userDto)).thenReturn(invoice);
        InvoicesResponseDto responseDto = invoicesService.create(dto,user);
    }
}