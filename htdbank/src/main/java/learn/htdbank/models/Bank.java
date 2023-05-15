package learn.htdbank.models;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private int bank_id;
    @Min(value = 1, message = "Routing number has to be at least 1.")
    private int routing_number;
    private List<Employee> employees = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public int getRouting_number() {
        return routing_number;
    }

    public void setRouting_number(int routing_number) {
        this.routing_number = routing_number;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

