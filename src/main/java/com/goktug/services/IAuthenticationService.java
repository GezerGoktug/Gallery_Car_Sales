package com.goktug.services;

import com.goktug.dto.AuthRequest;
import com.goktug.dto.AuthResponse;
import com.goktug.dto.DtoUser;
import com.goktug.dto.RefreshTokenRequest;

public interface IAuthenticationService {
    
    DtoUser register(AuthRequest request);

    AuthResponse authenticate(AuthRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request); 
}
