package com.attendance.invoices;

import com.attendance.user.User;
import com.attendance.user.UserDto;
import org.springframework.stereotype.Service;

@Service
public class InvoicesMapper {
    public Invoice toInvoice(InvoiceDto dto, UserDto userDto) {
        var invoice = new Invoice();
        invoice.setPrice(dto.price());
        invoice.setIssuedDate(dto.issuedDate());
        invoice.setSeller(dto.seller());
        invoice.setRecipient(dto.recipient());
        invoice.setStatus(dto.status());
        var user = new User();
        user.setId(userDto.id());
        invoice.setUser(user);
        return invoice;
    }
    public InvoicesResponseDto toInvoicesResponseDto(Invoice invoice) {
        return new InvoicesResponseDto(
                invoice.getId(),
                invoice.getRecipient(),
                invoice.getSeller(),
                invoice.getStatus(),
                invoice.getIssuedDate(),
                invoice.getPrice(),
                invoice.getUser().getFirstName()+" "+invoice.getUser().getLastName()
        );
    }
}
