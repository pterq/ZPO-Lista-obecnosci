package com.attendance.token;

import java.util.List;
import java.util.Optional;

import com.attendance.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join Students u\s
      on t.student.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    Optional<Token> findTopByUserAndTokenTypeAndRevokedFalseAndExpiredFalseOrderByIdDesc(Student student, TokenType tokenType);
}