package learn.htdbank.data;

import learn.htdbank.models.Customer;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.CDATASection;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll();

    @Transactional
    Customer findById(int id);

    Customer add(Customer customer);

    @Transactional
    boolean update(Customer customer);

    @Transactional
    boolean delete(int id);
