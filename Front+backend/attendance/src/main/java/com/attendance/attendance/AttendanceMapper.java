package com.attendance.attendance;

import com.attendance.student.Student;
import com.attendance.student.StudentDto;
import org.springframework.stereotype.Service;

@Service
public class AttendanceMapper {
    public Attendance toInvoice(AttendanceDto dto, StudentDto studentDto) {
        var invoice = new Attendance();

        var user = new Student();
        user.setId(studentDto.id());
        invoice.setStudent(user);
        return invoice;
    }
    public AttendanceResponseDto toInvoicesResponseDto(Attendance attendance) {
        return new AttendanceResponseDto(
                attendance.getId()
        );
    }
}
