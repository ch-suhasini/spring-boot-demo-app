package demo.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import demo.entity.Customer;
import demo.entity.Address;
import demo.exception.CreditRatingChkVioationException;
import demo.exception.CustomerNotFoundException;
import demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerResource {

    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public Resource<Customer> retrieveCustomer(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent())
            throw new CustomerNotFoundException("No Such Customer Exists with id-" + id);

        Resource<Customer> resource = new Resource<Customer>(customer.get());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllCustomers());

        resource.add(linkTo.withRel("all-customers"));

        return resource;
    }
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerRepository.deleteById(id);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        if(customer.getCreditRating()<0 || customer.getCreditRating()>100)
            throw new CreditRatingChkVioationException("Credit Rating range should be 1 to 100");
        Set<Address> addresses = customer.getAddress();
        customer.setAddress(null);

        for (Address address : addresses) {
            customer.addAddress(address);
        }

        Customer savedCustomer = customerRepository.save(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer, @PathVariable long id) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (!customerOptional.isPresent())
            return ResponseEntity.notFound().build();

        customer.setId(id);

        customerRepository.save(customer);

        return ResponseEntity.noContent().build();
    }
}
