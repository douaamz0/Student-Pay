package ma.emsi.studentpay.repo;

import ma.emsi.studentpay.entities.Payment;
import ma.emsi.studentpay.entities.PaymentStatus;
import ma.emsi.studentpay.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);

}
