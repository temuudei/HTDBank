package learn.htdbank.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Card {
    private int card_id;
    @NotBlank(message = "Type is required.")
    @Size(max = 10, message = "Type cannot be longer than 10 characters.")
    private String type;
    private int account_id;
    private int customer_id;
    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

}
