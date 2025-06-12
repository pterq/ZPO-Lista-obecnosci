package com.attendance.attendance;

import com.attendance.student.Student;
import com.attendance.student.StudentDto;
import com.attendance.student.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentMapper studentMapper;
    private final AttendanceMapper attendanceMapper;

    public AttendanceResponseDto create(AttendanceDto dto, Student student) {
       StudentDto studentDto = studentMapper.toUserDto(student);
       var invoice = attendanceMapper.toInvoice(dto, studentDto);
        return attendanceMapper.toInvoicesResponseDto(attendanceRepository.save(invoice));
    }


    public List<Attendance> findByUser(Student student)
    {
        return attendanceRepository.findByUser(student);
    }

    public Optional<AttendanceResponseDto> findById(Long invoiceId) {

        return attendanceRepository.findById(invoiceId).map
                (attendanceMapper::toInvoicesResponseDto);
    }

    public List<AttendanceResponseDto> findAll() {
        return attendanceRepository.findAll()
                .stream().map(attendanceMapper::toInvoicesResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long invoiceId) {
        attendanceRepository.deleteById(invoiceId);
    }
}
