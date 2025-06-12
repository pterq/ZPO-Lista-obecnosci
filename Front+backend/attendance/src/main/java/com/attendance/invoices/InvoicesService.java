package com.attendance.invoices;

import com.attendance.user.User;
import com.attendance.user.UserDto;
import com.attendance.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoicesService {
    private final InvoicesRepository invoicesRepository;
    private final UserMapper userMapper;
    private final InvoicesMapper invoicesMapper;

    public InvoicesResponseDto create(InvoiceDto dto,User user) {
       UserDto userDto = userMapper.toUserDto(user);
       var invoice = invoicesMapper.toInvoice(dto,userDto);
        return invoicesMapper.toInvoicesResponseDto(invoicesRepository.save(invoice));
    }


    public List<Invoice> findByUser(User user)
    {
        return invoicesRepository.findByUser(user);
    }

    public Optional<InvoicesResponseDto> findById(Long invoiceId) {

        return invoicesRepository.findById(invoiceId).map
                (invoicesMapper::toInvoicesResponseDto);
    }

    public List<InvoicesResponseDto> findAll() {
        return invoicesRepository.findAll()
                .stream().map(invoicesMapper::toInvoicesResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long invoiceId) {
        invoicesRepository.deleteById(invoiceId);
    }
}
