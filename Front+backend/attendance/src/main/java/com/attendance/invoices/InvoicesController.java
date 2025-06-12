package com.attendance.invoices;

import com.attendance.exceptions.InvoiceNotFoundException;
import com.attendance.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/invoices/")
public class InvoicesController {

    private final InvoicesService invoicesService;

    @PostMapping("create")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceDto dto, @AuthenticationPrincipal User user) {
        InvoicesResponseDto newInvoice = invoicesService.create(dto,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
    }

    @GetMapping("get")
    public ResponseEntity<?> getInvoices() {
        List<InvoicesResponseDto> invoices = invoicesService.findAll();
        return ResponseEntity.ok(invoices);
    }
    @GetMapping("get/{invoiceId}/view")
    public ResponseEntity<?> viewInvoice(@PathVariable Long invoiceId) {
        Optional<InvoicesResponseDto> invoiceOptional = invoicesService.findById(invoiceId);
        return ResponseEntity.ok(invoiceOptional.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found")));
    }
    @GetMapping("get/{invoiceId}/edit")
    public ResponseEntity<?> editInvoices(@PathVariable Long invoiceId) {
        Optional<InvoicesResponseDto> invoiceOptional = invoicesService.findById(invoiceId);
        return ResponseEntity.ok(invoiceOptional.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found")));
    }
    @DeleteMapping("get/{invoiceId}/delete")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long invoiceId)
    {
    Optional<InvoicesResponseDto> invoiceOptional = invoicesService.findById(invoiceId);
    if(invoiceOptional.isPresent()) {
        invoicesService.deleteById(invoiceId);
        return ResponseEntity.ok().build();
    }
    throw new InvoiceNotFoundException("Invoice not found");
}
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    )
    {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach(error ->{
            var fieldNmae = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldNmae, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
