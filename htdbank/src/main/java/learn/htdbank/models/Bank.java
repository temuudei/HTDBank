package learn.htdbank.models;
import javax.validation.constraints.*;
public class Bank {
    private int bank_id;
    @NotNull(message = "Routing number is required.")
    @Min(value = 1, message = "Routing number has to be greater than 0.")
    private int routing_number;

    public Bank() {
    }

    public Bank(int bank_id, int routing_number) {
        this.bank_id = bank_id;
        this.routing_number = routing_number;
    }

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
}

