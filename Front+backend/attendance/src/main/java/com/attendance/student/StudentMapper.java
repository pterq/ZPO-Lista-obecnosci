package com.attendance.student;

import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public StudentDto toUserDto(Student student)
    {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getFirstName()+" "+ student.getLastName(),
                student.getUsername(),
                student.getRole()
        );

    }
}
