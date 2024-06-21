package ma.emsi.studentpay.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.emsi.studentpay.DTO.NewPaymentDTO;
import ma.emsi.studentpay.entities.Payment;
import ma.emsi.studentpay.entities.PaymentStatus;
import ma.emsi.studentpay.entities.PaymentType;
import ma.emsi.studentpay.entities.Student;
import ma.emsi.studentpay.repo.PaymentRepository;
import ma.emsi.studentpay.repo.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO)
            throws IOException {

        Path forlderPath= Paths.get(System.getProperty("user.home"),"emsi-data","payments");
        if(!Files.exists(forlderPath)){
            Files.createDirectories(forlderPath);
        }
        String fileName= UUID.randomUUID().toString();
        Path filePath=Paths.get(System.getProperty("user.home"),"emsi-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student=studentRepository.findByCode(newPaymentDTO.getStudentCode());
        Payment payment=Payment.builder()
                .date(newPaymentDTO.getDate()).type(newPaymentDTO.getType()).student(student)
                .amount(newPaymentDTO.getAmount()).file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);

    }

    public Payment updatePaymentStatus(PaymentStatus status,Long id)
    {
        Payment payment=paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(  Long paymentId)throws IOException
    {
        Payment payment=paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
