package com.vscode.req.onlinevscode.login.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginContoller {
    @PostMapping("/login.do")
    public Boolean doLogin(){
        return true;
    }
}
