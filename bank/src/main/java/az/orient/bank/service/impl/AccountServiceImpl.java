package az.orient.bank.service.impl;

import az.orient.bank.dto.request.ReqAccount;
import az.orient.bank.dto.response.RespAccount;
import az.orient.bank.dto.response.RespCustomer;
import az.orient.bank.dto.response.RespStatus;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Account;
import az.orient.bank.entity.Customer;
import az.orient.bank.enums.EnumStatus;
import az.orient.bank.exception.BankException;
import az.orient.bank.exception.ExceptionConstants;
import az.orient.bank.repository.AccountRepository;
import az.orient.bank.repository.CustomerRepository;
import az.orient.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CustomerServiceImpl customerService;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespAccount>> accountList(Long customerId) {
        Response<List<RespAccount>> response = new Response<>();
        try {
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Account> accountList = accountRepository.findAllByCustomerAndActive(customer, EnumStatus.ACTIVE.getValue());
            if (accountList.isEmpty()) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
            }
            Iterator<Account> iterator = accountList.iterator();
            List<RespAccount> respAccountList = new ArrayList<>();
            while (iterator.hasNext()) {
                Account account = iterator.next();
                RespAccount respAccount = convert(account);
                respAccountList.add(respAccount);
            }
            response.setStatus(RespStatus.success());
            response.setT(respAccountList);
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
    public Response<RespAccount> getAccountById(Long accountId) {
        Response<RespAccount> response = new Response<>();
        try {
            if(accountId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Account account = accountRepository.findAccountByIdAndActive(accountId, EnumStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
            }
            RespAccount respAccount = convert(account);
            response.setStatus(RespStatus.success());
            response.setT(respAccount);
        }catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        }catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    public RespAccount convert(Account account) {
        return RespAccount.builder().
                id(account.getId())
                .accountNo(account.getAccountNo())
                .iban(account.getIban())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .accountDate(account.getCreatedDate())
                .respCustomer(customerService.convert(account.getCustomer()))
                .build();

    }

    @Override
    public Response<RespAccount> createAccount(ReqAccount reqAccount) {
        Response<RespAccount> response = new Response<>();
        try {
            if (reqAccount == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Long id=reqAccount.getCustomerId();
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Account account = Account.builder().
                    customer(customer).
                    accountNo(reqAccount.getAccountNo()).
                    balance(reqAccount.getBalance()).
                    iban(reqAccount.getIban()).
                    currency(reqAccount.getCurrency()).build();
            accountRepository.save(account);
            response.setStatus(RespStatus.success());
        }catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        }catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespAccount> updateAccount(ReqAccount reqAccount) {
        Response<RespAccount> response = new Response<>();
        try {
            if (reqAccount == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Long id = reqAccount.getId();
            if (id == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");

            }
            Account account = accountRepository.findAccountByIdAndActive(id, EnumStatus.ACTIVE.getValue());
            if (account == null) {
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found");
            }
            account.setAccountNo(reqAccount.getAccountNo());
            account.setIban(reqAccount.getIban());
            account.setCurrency(reqAccount.getCurrency());
            account.setBalance(reqAccount.getBalance());
            accountRepository.save(account);
            response.setT(convert(account));
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
    }
