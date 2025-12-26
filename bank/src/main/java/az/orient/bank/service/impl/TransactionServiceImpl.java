package az.orient.bank.service.impl;

import az.orient.bank.dto.request.ReqTransaction;
import az.orient.bank.dto.response.RespStatus;
import az.orient.bank.dto.response.RespTransaction;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Account;
import az.orient.bank.entity.Customer;
import az.orient.bank.entity.Transaction;
import az.orient.bank.enums.EnumStatus;
import az.orient.bank.exception.BankException;
import az.orient.bank.exception.ExceptionConstants;
import az.orient.bank.parser.response.RespCurrency;
import az.orient.bank.repository.AccountRepository;
import az.orient.bank.repository.CustomerRepository;
import az.orient.bank.repository.TransactionRepository;
import az.orient.bank.service.AccountService;
import az.orient.bank.service.TransactionService;
import az.orient.bank.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final AccountServiceImpl accountServiceImpl;
    private final TransactionRepository transactionRepository;
    private final Utility utility;
    private EntityManager em;
    @Value("${api.url}")
    private String apiUrl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response createTransaction(ReqTransaction reqTransaction) {
        Response response = new Response();
        try {
            Long accountId = reqTransaction.getDtAccountId();
            String currency = reqTransaction.getCurrency();
            Account account = accountRepository.findAccountByIdAndActive(accountId, EnumStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
            }
            Long customerId = reqTransaction.getCustomerId();
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            if (reqTransaction.getAmount() > account.getBalance()) {
                throw new BankException(ExceptionConstants.NOT_ENOUGH_BALANCE, "Not enough balance ");
            }


            if (!reqTransaction.getCurrency().equalsIgnoreCase(account.getCurrency())) {
                String result = utility.sendGet(apiUrl + currency);
                System.out.println(result);
                RespCurrency respCurrency = parse(result);
                if (respCurrency.getResult().equalsIgnoreCase("success")) {
                    Map<String, Double> rate = respCurrency.getRates();
                    Double value = rate.get(account.getCurrency());
                    double lastBalance = account.getBalance() - (reqTransaction.getAmount() * value);
                    account.setBalance(lastBalance);

                }
            } else
                account.setBalance(account.getBalance() - reqTransaction.getAmount());
            accountRepository.save(account);

            Transaction transaction = Transaction.builder()
                    .id(reqTransaction.getId())
                    .amount(reqTransaction.getAmount())
                    .crAccount(reqTransaction.getCrAccount())
                    .currency(reqTransaction.getCurrency())
                    .status(reqTransaction.getStatus())
                    .customer(customer)
                    .dtAccount(account)
                    .trDate(new Date())
                    .build();
            transactionRepository.save(transaction);

            RespTransaction respTransaction = fillTransaction(transaction);
            response.setT(respTransaction);
            response.setStatus(RespStatus.success());
            // em.getTransaction().commit();
        } catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));

        }

        return response;
    }

    @Override
    public Response<List<RespTransaction>> getAllTransaction(Long customerId, Long accountId) {
        Response<List<RespTransaction>> response = new Response<>();
        try {
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumStatus.ACTIVE.getValue());
            Account account = accountRepository.findAccountByIdAndActive(accountId, EnumStatus.ACTIVE.getValue());
            System.out.println(account);
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Transaction> transactionList1 = null;

            if (accountId != null) {
                transactionList1 = transactionRepository.findTransactionByCustomerAndDtAccount(customer, account);
                if (account == null) {
                    throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
                }
            } else {
                transactionList1 = transactionRepository.findTransactionByCustomerAndStatus(customer, EnumStatus.ACTIVE.getValue());
            }
            List<RespTransaction> respTransactionList = transactionList1
                    .stream()
                    .map(this::fillTransaction)
                    .toList();

            response.setT(respTransactionList);
            response.setStatus(RespStatus.success());
        } catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    public RespTransaction fillTransaction(Transaction transaction) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return RespTransaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .crAccount(transaction.getCrAccount())
                .currency(transaction.getCurrency())
                .status(transaction.getStatus())
                .createDate(transaction.getTrDate() != null ? dateFormat.format(transaction.getTrDate()) : null)
                .customer(transaction.getCustomer())
                .dtAccount(transaction.getDtAccount())
                .build();
    }


    private RespCurrency parse(String response) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
//        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        RespCurrency rates = mapper.readValue(response, RespCurrency.class);
        return rates;
    }

}
