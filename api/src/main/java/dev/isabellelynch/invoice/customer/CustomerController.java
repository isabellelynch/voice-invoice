package dev.isabellelynch.invoice.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    public record CustomerRequest(
            @NotBlank @Size(max = 120) String name,
            @Email @Size(max = 200) String email,
            @Size(max = 300) String address) {
    }

    public record CustomerResponse(Long id, String name, String email) {
        static CustomerResponse from(Customer c) {
            return new CustomerResponse(c.getId(), c.getName(), c.getEmail());
        }
    }

    private final CustomerRepository customers;

    public CustomerController(CustomerRepository customers) {
        this.customers = customers;
    }

    @GetMapping
    public List<CustomerResponse> list() {
        return customers.findAll(Sort.by("name")).stream().map(CustomerResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerRequest req) {
        String email = (req.email() == null || req.email().isBlank()) ? null : req.email().trim();
        Customer saved = customers.save(new Customer(req.name().trim(), email, req.address()));
        return CustomerResponse.from(saved);
    }
}
