package az.orient.bank.controller;

import az.orient.bank.dto.request.ReqAccount;
import az.orient.bank.dto.response.RespAccount;
import az.orient.bank.dto.response.Response;
import az.orient.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/list")
    public Response<List<RespAccount>> accountList(@RequestParam("customerId") Long customerId) {
        return accountService.accountList(customerId);
    }

    @GetMapping("/get/{accountId}")
    public Response<RespAccount> getAccount(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/create")
    public Response<RespAccount> createAccount(@RequestBody ReqAccount reqAccount) {
        return accountService.createAccount(reqAccount) ;
    }

    @PutMapping("/update")
    public Response<RespAccount> updateAccount(@RequestBody ReqAccount reqAccount) {
        return accountService.updateAccount(reqAccount);
    }

    @DeleteMapping("/delete/{accountId}")
    public Response deleteAccount(@PathVariable Long accountId) {
        return null;
    }




}
