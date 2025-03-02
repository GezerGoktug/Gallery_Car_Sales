package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestAuthenticationController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.AuthRequest;
import com.goktug.dto.AuthResponse;
import com.goktug.dto.DtoUser;
import com.goktug.dto.RefreshTokenRequest;
import com.goktug.services.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/auth")
public class RestAuthenticationController extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest request) {
        return ok(authenticationService.register(request), 200);
    }

    @PostMapping("/authenticate")
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {

        return ok(authenticationService.authenticate(request), 200);
    }

    @PostMapping("/refreshToken")
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ok(authenticationService.refreshToken(request), 200);
    }

}
