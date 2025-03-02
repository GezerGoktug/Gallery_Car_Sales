package com.goktug.controllers;


import com.goktug.dto.AuthRequest;
import com.goktug.dto.AuthResponse;
import com.goktug.dto.DtoUser;
import com.goktug.dto.RefreshTokenRequest;



public interface IRestAuthenticationController {

    RootEntity<DtoUser> register(AuthRequest request);

    RootEntity<AuthResponse> authenticate(AuthRequest request);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}