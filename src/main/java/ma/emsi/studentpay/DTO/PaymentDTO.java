package ma.emsi.studentpay.DTO;

import jakarta.persistence.ManyToOne;
import ma.emsi.studentpay.entities.PaymentStatus;
import ma.emsi.studentpay.entities.PaymentType;
import ma.emsi.studentpay.entities.Student;

import java.time.LocalDate;

public class PaymentDTO {
    private long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
}
