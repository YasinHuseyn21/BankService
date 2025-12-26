package az.orient.bank.repository;

import az.orient.bank.dto.response.RespAccount;
import az.orient.bank.entity.Account;
import az.orient.bank.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account>  findAllByCustomerAndActive(Customer customer, Integer active);
    Account findAccountByIdAndActive(Long id, Integer active);
}
