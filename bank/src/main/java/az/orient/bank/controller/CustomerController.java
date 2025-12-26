package az.orient.bank.controller;

import az.orient.bank.dto.request.ReqCustomer;
import az.orient.bank.dto.response.RespCustomer;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Customer;
import az.orient.bank.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    @GetMapping("/list")
    public Response<List<RespCustomer>> getCustomerList() {
        return customerService.getCustomerList();
    }


    @GetMapping("/byId/{id}")
    public Response<RespCustomer> getCustomerById(@PathVariable("id") Long customerId) {
        return customerService.getCustomerById(customerId);
    }


    @PostMapping("/create")
    public Response<RespCustomer> createCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.createCustomer(reqCustomer);
    }


    @PutMapping("/update")
    public Response<RespCustomer> updateCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.updateCustomer(reqCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }


}
