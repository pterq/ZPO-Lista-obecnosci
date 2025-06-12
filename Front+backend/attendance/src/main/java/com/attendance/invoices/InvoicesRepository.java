package com.attendance.invoices;

import com.attendance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoicesRepository  extends JpaRepository<Invoice,Long> {
    List<Invoice> findByUser(User user);
}
