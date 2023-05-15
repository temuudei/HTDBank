package learn.htdbank.data;

import learn.htdbank.models.Bank;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BankRepository {
    @Transactional
    List<Bank> findAll();
    @Transactional
    Bank findById(int bank_id);
    @Transactional
    Bank add(Bank bank);
    @Transactional
    boolean update(Bank bank);
    @Transactional
    boolean deleteById(int bank_id);
}
