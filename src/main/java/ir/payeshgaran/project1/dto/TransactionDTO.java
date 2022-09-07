package ir.payeshgaran.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    private String depositor;

    @NotBlank(message = "Receiver can not be empty")
    @Size(min = 5,message = "Receiver must be at least 5 character")
    private String receiver;

    @Min(value = 500 , message = "Transaction amount must be at least 500")
    private double amount;
}
