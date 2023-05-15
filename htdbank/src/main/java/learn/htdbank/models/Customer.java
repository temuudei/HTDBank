package learn.htdbank.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customer_id;
    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot be greater than 50 characters.")
    private String first_name;
    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot be greater than 50 characters.")
    private String last_name;
    @Min(value = 1, message = "Social security number has to be at least 1.")
    private int ssn;
    private List<Card> cards = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}