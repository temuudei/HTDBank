package learn.htdbank.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Customer {
    private int customer_id;
    @NotBlank(message = "First name is required.")
    private String first_name;
    @NotBlank(message = "Last name is required.")
    private String last_name;
    @Min(value = 1, message = "Social security number has to be at least 1.")
    private int ssn;
    private int account_id;
}