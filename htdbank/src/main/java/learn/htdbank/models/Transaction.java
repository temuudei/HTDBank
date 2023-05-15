package learn.htdbank.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Transaction {
    private int transaction_id;
    @NotBlank(message = "Transaction type is required.")
    @Size(max = 10, message = "Transaction cannot be longer than 10 characters.")
    private String transaction_type;
    @NotNull(message = "Transaction amount is required.")
    @Min(value = 0, message = "Transaction amount has to be at least 0.")
    private BigDecimal amount;

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
