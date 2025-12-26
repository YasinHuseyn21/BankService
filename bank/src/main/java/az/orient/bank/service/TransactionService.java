package az.orient.bank.service;

import az.orient.bank.dto.request.ReqTransaction;
import az.orient.bank.dto.response.RespTransaction;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    Response createTransaction(ReqTransaction reqTransaction);

    Response<List<RespTransaction>> getAllTransaction(Long customerId, Long accountId);
}
