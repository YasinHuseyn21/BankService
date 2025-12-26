package az.orient.bank.repository;

import az.orient.bank.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {


    List<Customer> findAllByActive(Integer active);

    Customer findCustomerByIdAndActive(Long id, Integer active);

}
