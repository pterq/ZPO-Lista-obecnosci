package com.attendance.user;

import com.attendance.token.Token;
import com.attendance.token.TokenRepository;
import com.attendance.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    public String getRefreshToken(User user) {
        Optional<Token> refreshToken = tokenRepository.findTopByUserAndTokenTypeAndRevokedFalseAndExpiredFalseOrderByIdDesc(
                user, TokenType.REFRESH);

        return refreshToken.map(Token::getToken).orElse(null);
    }
}
