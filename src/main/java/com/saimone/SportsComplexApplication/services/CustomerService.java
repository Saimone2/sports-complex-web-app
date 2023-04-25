package com.saimone.SportsComplexApplication.services;

import com.saimone.SportsComplexApplication.models.Attendance;
import com.saimone.SportsComplexApplication.models.ClubCard;
import com.saimone.SportsComplexApplication.models.Customer;
import com.saimone.SportsComplexApplication.models.MedicalExamination;
import com.saimone.SportsComplexApplication.repositories.AttendanceRepo;
import com.saimone.SportsComplexApplication.repositories.CustomerRepo;
import com.saimone.SportsComplexApplication.repositories.MedicalExaminationRepo;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final ClubCardService clubCardService;
    private final MedicalExaminationRepo medicalExaminationRepo;
    private final AttendanceRepo attendanceRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, ClubCardService clubCardService,
                           MedicalExaminationRepo medicalExaminationRepo, AttendanceRepo attendanceRepo) {
        this.customerRepo = customerRepo;
        this.clubCardService = clubCardService;
        this.medicalExaminationRepo = medicalExaminationRepo;
        this.attendanceRepo = attendanceRepo;
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer"));
    }

    public void createCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public void updateCustomer(Integer id, Customer customer) {
        Customer updatedCustomer = getCustomerById(id);
        updatedCustomer.setLastName(customer.getLastName());
        updatedCustomer.setFirstName(customer.getFirstName());
        updatedCustomer.setAddress(customer.getAddress());
        updatedCustomer.setEmail(customer.getEmail());
        customerRepo.save(updatedCustomer);
    }

    public void deleteCustomer(Integer id) {
        Customer customer = getCustomerById(id);

        List<ClubCard> clubCardList = clubCardService.getClubCardByCustomerId(id);
        for(ClubCard clubCard : clubCardList) {
            clubCardService.deleteClubCard(clubCard.getId());
        }
        List<MedicalExamination> medicalExaminationList = medicalExaminationRepo.findAllByCustomerId(id);
        for(MedicalExamination medicalExamination : medicalExaminationList) {
            medicalExaminationRepo.deleteById(medicalExamination.getId());
        }
        List<Attendance> attendanceList = attendanceRepo.findAllByCustomerId(id);
        for(Attendance attendance : attendanceList) {
            attendanceRepo.deleteById(attendance.getId());
        }
        customerRepo.delete(customer);
    }
}
