package az.orient.bank.controller;

import az.orient.bank.dto.request.ReqTransaction;
import az.orient.bank.dto.response.RespTransaction;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Transaction;
import az.orient.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping("/create")
    public Response createTransaction(@RequestBody ReqTransaction reqTransaction) {
        return transactionService.createTransaction(reqTransaction);
    }

    @GetMapping("/list")
    public Response<List<RespTransaction>> getTransactionList(@RequestParam Long customerId, Long accountId) {
        return transactionService.getAllTransaction(customerId, accountId);
    }

}
