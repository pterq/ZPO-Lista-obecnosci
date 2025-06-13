package com.attendance.student;

import com.attendance.token.Token;
import com.attendance.token.TokenRepository;
import com.attendance.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final TokenRepository tokenRepository;
    public String getRefreshToken(Student student) {
        Optional<Token> refreshToken = tokenRepository.findTopByStudentAndTokenTypeAndRevokedFalseAndExpiredFalseOrderByIdDesc(
                student, TokenType.REFRESH);

        return refreshToken.map(Token::getToken).orElse(null);
    }
}
