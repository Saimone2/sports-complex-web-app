package com.saimone.SportsComplexApplication.services;

import com.saimone.SportsComplexApplication.models.*;
import com.saimone.SportsComplexApplication.repositories.*;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ClubCardService {

    private final ClubCardRepo clubCardRepo;
    private final CardSetsService cardSetsService;
    private final CardSetsRepo cardSetsRepo;
    private final CustomerRepo customerRepo;
    private final AttendanceModeRepo attendanceModeRepo;
    private final ServiceRepo serviceRepo;

    @Autowired
    public ClubCardService(ClubCardRepo clubCardRepo, CardSetsService cardSetsService, CardSetsRepo cardSetsRepo, CustomerRepo customerRepo, AttendanceModeRepo attendanceModeRepo, ServiceRepo serviceRepo) {
        this.clubCardRepo = clubCardRepo;
        this.cardSetsService = cardSetsService;
        this.cardSetsRepo = cardSetsRepo;
        this.customerRepo = customerRepo;
        this.attendanceModeRepo = attendanceModeRepo;
        this.serviceRepo = serviceRepo;
    }

    public List<ClubCard> getAllClubCards() {
        return clubCardRepo.findAll();
    }

    public ClubCard getClubCardById(Integer id) {
        return clubCardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubCard"));
    }

    public List<ClubCard> getClubCardByCustomerId(Integer id) {
        return clubCardRepo.findAllByCustomerId(id);
    }

    public void createClubCard(ClubCard clubCard, Integer customerId, Integer attendanceModeId, List<Integer> services) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        AttendanceMode attendanceMode = attendanceModeRepo.findById(attendanceModeId).orElseThrow();

        clubCard.setCustomer(customer);
        clubCard.setAttendanceMode(attendanceMode);

        List<Service> serviceList = new ArrayList<>();
        for(Integer serviceId : services) {
            Service service = serviceRepo.findById(serviceId).orElseThrow();
            serviceList.add(service);
        }
        clubCardRepo.save(clubCard);
        cardSetsService.changeSet(clubCard, serviceList);
    }

    public void updateClubCard(Integer id, ClubCard clubCard, Integer customerId, Integer attendanceModeId, List<Integer> services) {
        ClubCard updatedClubCard = getClubCardById(id);

        Customer customer = customerRepo.findById(customerId).orElseThrow();
        AttendanceMode attendanceMode = attendanceModeRepo.findById(attendanceModeId).orElseThrow();

        List<Service> serviceList = new ArrayList<>();
        for(Integer serviceId : services) {
            Service service = serviceRepo.findById(serviceId).orElseThrow();
            serviceList.add(service);
        }
        cardSetsService.changeSet(updatedClubCard, serviceList);

        updatedClubCard.setCustomer(customer);
        updatedClubCard.setIssueDate(clubCard.getIssueDate());
        updatedClubCard.setExpireDate(clubCard.getExpireDate());
        updatedClubCard.setAttendanceMode(attendanceMode);
        clubCardRepo.save(updatedClubCard);
    }

    public void deleteClubCard(Integer id) {
        ClubCard clubCard = getClubCardById(id);

        List<CardSets> list = cardSetsRepo.findAllByClubCardId(id);
        for(CardSets cardSet : list) {
            cardSetsRepo.deleteById(cardSet.getId());
        }
        clubCardRepo.delete(clubCard);
    }

    public List<Integer> getServiceList(Integer id) {
        List<Integer> list = new ArrayList<>();
        List<CardSets> cardSets = cardSetsRepo.findAllByClubCardId(id);
        for(CardSets cardSet : cardSets) {
            list.add(cardSet.getService().getId());
        }
        return list;
    }
}
