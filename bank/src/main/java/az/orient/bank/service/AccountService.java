package az.orient.bank.service;

import az.orient.bank.dto.request.ReqAccount;
import az.orient.bank.dto.response.RespAccount;
import az.orient.bank.dto.response.Response;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountService{
    Response<List<RespAccount>> accountList(@RequestParam Long customerId);

    Response<RespAccount> getAccountById(Long accountId);

    Response<RespAccount> createAccount(ReqAccount reqAccount);

    Response<RespAccount> updateAccount(ReqAccount reqAccount);

    Response deleteAccount(Long accountId);
}
