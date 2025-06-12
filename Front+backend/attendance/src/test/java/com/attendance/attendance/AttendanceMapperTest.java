package com.attendance.attendance;

import com.attendance.student.Role;
import com.attendance.student.Student;
import com.attendance.student.StudentDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.attendance.student.Role.ADMIN;

import java.time.LocalDate;
import java.util.ArrayList;

class AttendanceMapperTest {
    private AttendanceMapper attendanceMapper;

    @BeforeEach
    void setUp() {
        System.out.println("###########Start of Test###############");
        attendanceMapper = new AttendanceMapper();
    }

    @AfterEach
    void tearDown() {
        System.out.println("###########End of Test###############");
    }

    @Test
    public void shouldMapInvoiceDtoToInvoice() {

        AttendanceDto dto = new AttendanceDto(
                1L,
                "John Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                2137.69,
                null
        );
        StudentDto user = new StudentDto(
                7,
                "Jan",
                "Doe",
                "Jan Doe",
                "xxx@yyy.zz",
                "123",
                Role.MANAGER
        );
        Attendance attendance = attendanceMapper.toInvoice(dto, user);
        Assertions.assertEquals(dto.recipient(), attendance.getRecipient());
        Assertions.assertEquals(dto.seller(), attendance.getSeller());
        Assertions.assertEquals(dto.status(), attendance.getStatus());
        Assertions.assertEquals(dto.price(), attendance.getPrice());
        Assertions.assertEquals(user.id(), attendance.getStudent().getId());

    }

    @Test
    void toInvoicesResponseDto() {
        Student student = new Student(8, "Jim", "Doe", "Jim Doe", "zzz@yyy.xx", "456", ADMIN, new ArrayList<>(), new ArrayList<>());
        attendanceMapper = new AttendanceMapper();
        Attendance attendance = new Attendance(
                2L,
                "Jane Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                1000.52,
                student
        );
        AttendanceResponseDto dto = attendanceMapper.toInvoicesResponseDto(attendance);

        Assertions.assertEquals(attendance.getId(), dto.id());
        Assertions.assertEquals(attendance.getRecipient(), dto.recipient());
        Assertions.assertEquals(attendance.getSeller(), dto.seller());
        Assertions.assertEquals(attendance.getStatus(), dto.status());
        Assertions.assertEquals(attendance.getIssuedDate(), dto.issuedDate());
        Assertions.assertEquals(attendance.getPrice(), dto.price());
        Assertions.assertEquals(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName(), dto.fullName());
    }
}