package com.goktug.controllers;

public class RestBaseController {

    public <T> RootEntity<T> ok(T data, Integer status) {
        return RootEntity.ok(data, status);
    }

    public <T> RootEntity<T> error(String error, Integer status) {
        return RootEntity.error(error, status);
    }
}
