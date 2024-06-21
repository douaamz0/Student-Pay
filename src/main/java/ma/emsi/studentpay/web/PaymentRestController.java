package ma.emsi.studentpay.web;

import lombok.AllArgsConstructor;
import ma.emsi.studentpay.DTO.NewPaymentDTO;
import ma.emsi.studentpay.entities.Payment;
import ma.emsi.studentpay.entities.PaymentStatus;
import ma.emsi.studentpay.entities.PaymentType;
import ma.emsi.studentpay.entities.Student;
import ma.emsi.studentpay.repo.PaymentRepository;
import ma.emsi.studentpay.repo.StudentRepository;
import ma.emsi.studentpay.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class PaymentRestController {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @GetMapping(path="/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }

    @GetMapping(path="/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable  String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path="/payments/ByStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    @GetMapping(path="/payments/ByType")
    public List<Payment> paymentsByType(PaymentType type){
        return paymentRepository.findByType(type);
    }

    @GetMapping(path="/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();

    }
    @GetMapping(path="/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping(path="students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }

    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam  PaymentStatus status, @PathVariable Long id)
    {

        return paymentService.updatePaymentStatus(status,id);
    }


    @PostMapping(path= "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file, NewPaymentDTO newPaymentDTO)
    throws IOException {

       return this.paymentService.savePayment(file,newPaymentDTO);

    }


    @GetMapping(path="paymentFile/{paymentId}", produces=MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile( @PathVariable Long paymentId)throws IOException
    {
        return paymentService.getPaymentFile(paymentId);
    }



}
