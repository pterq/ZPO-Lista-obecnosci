package com.attendance.token;

import java.util.List;
import java.util.Optional;

import com.attendance.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
      select t from Token t
      where t.student.id = :id and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    Optional<Token> findTopByStudentAndTokenTypeAndRevokedFalseAndExpiredFalseOrderByIdDesc(Student student, TokenType tokenType);
}
