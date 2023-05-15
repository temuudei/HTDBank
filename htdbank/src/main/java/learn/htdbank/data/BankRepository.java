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
    Bank add(Bank account);
    @Transactional
    boolean update(Bank account);
    @Transactional
    boolean deleteById(int bank_id);
}
