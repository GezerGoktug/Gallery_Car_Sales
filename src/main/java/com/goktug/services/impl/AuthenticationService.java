package com.goktug.services.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.goktug.dto.AuthRequest;
import com.goktug.dto.AuthResponse;
import com.goktug.dto.DtoUser;
import com.goktug.dto.RefreshTokenRequest;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.jwt.JwtService;
import com.goktug.models.RefreshToken;
import com.goktug.models.User;
import com.goktug.repositories.RefreshTokenRepository;
import com.goktug.repositories.UserRepository;
import com.goktug.services.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private User createUser(AuthRequest request) {

        User newUser = new User();

        newUser.setCreatedAt(new Date());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return newUser;

    }

    public DtoUser register(AuthRequest request) {

        User user = userRepository.save(createUser(request));

        DtoUser dtoUser = new DtoUser();

        BeanUtils.copyProperties(user, dtoUser);

        return dtoUser;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setCreatedAt(new Date());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        return refreshToken;

    }

    private boolean isValidRefreshToken(Date expireDate) {
        return new Date().before(expireDate);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
       

        if (refreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(request.getRefreshToken(), MessageType.REFRESH_TOKEN_NOT_FOUND),
                    401);
        }
        if (!isValidRefreshToken(refreshToken.get().getExpireDate())) {
            throw new BaseException(new ErrorMessage(request.getRefreshToken(), MessageType.REFRESH_TOKEN_EXPIRED_DATE),
                    401);
        }

        User user = refreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken newRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, newRefreshToken.getRefreshToken());
    }

    public AuthResponse authenticate(AuthRequest request) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());

            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optUser = userRepository.findByUsername(request.getUsername());

            String accessToken = jwtService.generateToken(optUser.get());

            RefreshToken refreshToken = createRefreshToken(optUser.get());

            RefreshToken dbRefreshToken = refreshTokenRepository.save(refreshToken);

            return new AuthResponse(accessToken, dbRefreshToken.getRefreshToken());

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.USERNAME_OR_PASSWORD_INVALID), 404);
        }

    }
}
