package com.attendance.attendance;

import com.attendance.student.Role;
import com.attendance.student.Student;
import com.attendance.student.StudentDto;
import com.attendance.student.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.attendance.student.Role.ADMIN;

class AttendanceServiceTest {
    @InjectMocks
    private AttendanceService attendanceService;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private AttendanceMapper attendanceMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldSuccesffulySaveAInvoice()
    {
        AttendanceDto dto = new AttendanceDto(
                1L,
                "John Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                2137.69,
                null
        );
        Student student = new Student
                (8,
                        "Jim",
                        "Doe",
                        "Jim Doe",
                        "zzz@yyy.xx",
                        "456",
                        ADMIN, new ArrayList<>(),
                        new ArrayList<>()
                );
        StudentDto studentDto = new StudentDto(
                7,
                "Jan",
                "Doe",
                "Jan Doe",
                "xxx@yyy.zz",
                "123",
                Role.MANAGER
        );
        Attendance attendance = new Attendance(
                2L,
                "Jane Doe",
                "Ja",
                Status.NEW,
                LocalDate.now(),
                1000.52,
                student
        );
        Mockito.when(studentMapper.toUserDto(student)).thenReturn(studentDto);
        Mockito.when(attendanceMapper.toInvoice(dto, studentDto)).thenReturn(attendance);
        AttendanceResponseDto responseDto = attendanceService.create(dto, student);
    }
}