package com.example.demo;

import com.example.demo.entities.Customer;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.support.GenericSpecification;
import com.example.demo.support.SearchCriteria;
import com.example.demo.support.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 6; i++) {
            Customer customer = new Customer();
            customer.setFirstName("amine"+i);
            customer.setLastName("hamzi"+i);
            customerRepository.save(customer);
        }
        Customer customer = new Customer();
        customer.setLastName("hamzi1");
        List<Customer> customerList = findBeneficiariesWithPredicates(customer);
        customerList.forEach(System.out::println);
    }

    public List<Customer> findBeneficiariesWithPredicates(Customer request) {
        GenericSpecification<Customer> genericSpecification = new GenericSpecification<>();
        if(request.getFirstName()!=null)
        {
            genericSpecification.add(new SearchCriteria("firstName",request.getFirstName(), SearchOperation.EQUAL));
        }
        if(!request.getLastName().isEmpty())
        {
            genericSpecification.add(new SearchCriteria("lastName",request.getLastName(), SearchOperation.EQUAL));
        }
        return customerRepository.findAll(genericSpecification);
    }
}
