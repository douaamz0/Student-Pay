package ma.emsi.studentpay;

import ma.emsi.studentpay.entities.Payment;
import ma.emsi.studentpay.entities.PaymentStatus;
import ma.emsi.studentpay.entities.PaymentType;
import ma.emsi.studentpay.entities.Student;
import ma.emsi.studentpay.repo.PaymentRepository;
import ma.emsi.studentpay.repo.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class StudentPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentPayApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Ghizlane").code("112233").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Douaa").code("112244").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Yassmine").code("112255").programId("GLSID")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Lamiae").code("112266").programId("BDCC")
                    .build());

            PaymentType[] paymentTypes=PaymentType.values();
            Random randoms=new Random();

            studentRepository.findAll().forEach(student -> {
                for(int i=0;i<10;i++){
                    int index= randoms.nextInt(paymentTypes.length);
                    Payment payment=Payment.builder()
                            .amount(1000+(int)(Math.random()+20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(student)
                            .build();
                    paymentRepository.save(payment);
                }
                    }

            );
        };
    }


}
