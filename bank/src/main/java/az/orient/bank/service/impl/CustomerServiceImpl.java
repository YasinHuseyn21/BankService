package az.orient.bank.service.impl;

import az.orient.bank.dto.request.ReqCustomer;
import az.orient.bank.dto.response.RespCustomer;
import az.orient.bank.dto.response.RespStatus;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Customer;
import az.orient.bank.enums.EnumStatus;
import az.orient.bank.exception.BankException;
import az.orient.bank.exception.ExceptionConstants;
import az.orient.bank.repository.CustomerRepository;
import az.orient.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    @Override
    public Response<List<RespCustomer>> getCustomerList() {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            List<Customer> customerList = customerRepository.findAllByActive(EnumStatus.ACTIVE.getValue());
            List<RespCustomer> respCustomers = customerList.stream().map(this::convert).toList();
            if (respCustomers.isEmpty()) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            response.setT(respCustomers);
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

    @Override
    public Response<RespCustomer> getCustomerById(Long customerId) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (customerId == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = convert(customer);
            response.setT(respCustomer);
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

    @Override
    public Response<RespCustomer> createCustomer(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || surname == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = Customer.builder()
                    .name(name)
                    .surname(surname)
                    .email(reqCustomer.getEmail())
                    .pin(reqCustomer.getPin())
                    .seria(reqCustomer.getSeria())
                    .dob(reqCustomer.getDob())
                    .cif(reqCustomer.getCif())
                    .build();
            customerRepository.save(customer);
            response.setT(convert(customer));
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

    @Override
    public Response<RespCustomer> updateCustomer(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (reqCustomer == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Long id = reqCustomer.getId();
            if (id == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer = Customer.builder()
                    .id(reqCustomer.getId())
                    .name(reqCustomer.getName())
                    .surname(reqCustomer.getSurname())
                    .email(reqCustomer.getEmail())
                    .dob(reqCustomer.getDob())
                    .pin(reqCustomer.getPin())
                    .cif(reqCustomer.getCif())
                    .seria(reqCustomer.getSeria())
                    .createdDate(customer.getCreatedDate())
                    .active(customer.getActive())
                    .build();
            customerRepository.save(customer);
            response.setT(convert(customer));
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

    @Override
    public Response deleteCustomer(Long id) {
        Response<RespCustomer> response = new Response<>();
        try{

            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setActive(EnumStatus.DEACTIVE.getValue());
            response.setStatus(RespStatus.success());
            customerRepository.save(customer);
        } catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }


    public RespCustomer convert(Customer customer) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return RespCustomer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .dob(df.format(customer.getDob()))
                .email(customer.getEmail())
                .cif(customer.getCif())
                .seria(customer.getSeria())
                .pin(customer.getPin())
                .build();
    }



}
