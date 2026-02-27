package az.orient.bank.repository;

import az.orient.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {


    List<Customer> findAllByActive(Integer active);

    Customer findCustomerByIdAndActive(Long id, Integer active);

    Optional<Customer> findByEmail(String username);

}
