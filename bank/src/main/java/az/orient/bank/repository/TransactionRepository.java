package az.orient.bank.repository;

import az.orient.bank.entity.Account;
import az.orient.bank.entity.Customer;
import az.orient.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

   List<Transaction>  findTransactionByCustomerAndStatus(Customer customer,Integer active);

    List<Transaction> findTransactionByCustomerAndDtAccount(Customer customer, Account account);

}
