package learn.htdbank.data;

import learn.htdbank.models.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll();

    @Transactional
    Transaction findById(int id);

    Transaction add(Transaction trans);

    @Transactional
    boolean update(Transaction trans);

    @Transactional
    boolean delete(int id);
}
