package ma.emsi.studentpay.DTO;

import lombok.*;
import ma.emsi.studentpay.entities.PaymentType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentDTO {
    private double amount;
    private PaymentType type;
    private LocalDate date;
    private String studentCode;


}
