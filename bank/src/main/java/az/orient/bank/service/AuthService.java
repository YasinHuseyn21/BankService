package az.orient.bank.service;

import az.orient.bank.dto.request.ReqAuth;
import az.orient.bank.dto.response.RespAuth;
import az.orient.bank.dto.response.Response;

public interface AuthService {
    Response login(ReqAuth reqAuth);

    Response logout(String token,String username);
}
