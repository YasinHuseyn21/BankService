package az.orient.bank.controller;

import az.orient.bank.dto.request.ReqAuth;
import az.orient.bank.dto.response.Response;
import az.orient.bank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authservice;


    @PostMapping("/login")
    public Response auth(@RequestBody ReqAuth reqAuth) {
        return authservice.login(reqAuth);
    }

    @PostMapping("/logout")
    public Response logout(@RequestHeader String token, @RequestParam String username) {
        return authservice.logout(token, username);
    }

}
