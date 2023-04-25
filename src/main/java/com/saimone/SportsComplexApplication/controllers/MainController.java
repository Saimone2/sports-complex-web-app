package com.saimone.SportsComplexApplication.controllers;

import com.saimone.SportsComplexApplication.models.*;
import com.saimone.SportsComplexApplication.repositories.*;
import com.saimone.SportsComplexApplication.services.ClubCardService;
import com.saimone.SportsComplexApplication.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    private final CustomerService customerService;
    private final AttendanceModeRepo attendanceModeRepo;
    private final ClubCardService clubCardService;
    private final CardSetsRepo cardSetsRepo;
    private final ServiceRepo serviceRepo;

    @Autowired
    public MainController(CustomerService customerService, AttendanceModeRepo attendanceModeRepo, ClubCardService clubCardService, CardSetsRepo cardSetsRepo, ServiceRepo serviceRepo) {
        this.customerService = customerService;
        this.attendanceModeRepo = attendanceModeRepo;
        this.clubCardService = clubCardService;
        this.cardSetsRepo = cardSetsRepo;
        this.serviceRepo = serviceRepo;
    }

    @GetMapping("/customers")
    public String customersList(Model model){
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("newCustomer", new Customer());
        return "customers";
    }

    @PostMapping("/customers")
    public String createCustomer(@ModelAttribute("newCustomer") @Valid Customer customer,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "customers";
        }
        customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customer-update/{id}")
    public String updateCustomerForm(@PathVariable("id") Integer id, Model model){
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer-update";
    }

    @PostMapping("/customer-update/{id}")
    public String updateCustomer(@PathVariable("id") Integer id, @ModelAttribute("customer") @Valid Customer customer,
                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer-update";
        }
        customerService.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/customer-delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping("/club-cards")
    public String clubCardList(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("modes", attendanceModeRepo.findAll());
        model.addAttribute("sets", cardSetsRepo.findAll());
        model.addAttribute("services", serviceRepo.findAll());
        model.addAttribute("clubCards", clubCardService.getAllClubCards());
        model.addAttribute("newClubCard", new ClubCard());
        return "club-cards";
    }

    @PostMapping("/club-cards")
    public String createClubCard(@ModelAttribute("newClubCard") ClubCard clubCard, BindingResult bindingResult,
                                 @ModelAttribute("customerId") Integer customerId, @ModelAttribute("attendanceModeId") Integer attendanceModeId,
                                 @ModelAttribute("pool") String pool, @ModelAttribute("fitness") String fitness,
                                 @ModelAttribute("gym") String gym, @ModelAttribute("massage") String massage, @ModelAttribute("sauna") String sauna) {
        if(bindingResult.hasErrors()) {
            return "club-cards";
        }
        List<Integer> services = createServiceList(pool, fitness, gym, massage, sauna);
        clubCardService.createClubCard(clubCard, customerId, attendanceModeId, services);
        return "redirect:/club-cards";
    }

    @GetMapping("/club-card-update/{id}")
    public String updateClubCardForm(@PathVariable("id") Integer id, Model model){
        ClubCard clubCard = clubCardService.getClubCardById(id);
        model.addAttribute("clubCard", clubCard);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("modes", attendanceModeRepo.findAll());

        List<Integer> services = clubCardService.getServiceList(id);
        boolean pool = false, fitness = false, gym = false, massage = false,sauna = false;
        for(Integer service : services) {
            if(service == 1) {
                pool = true;
            } else if (service == 2) {
                fitness = true;
            } else if (service == 3) {
                gym = true;
            } else if (service == 4) {
                massage = true;
            } else if (service == 5) {
                sauna = true;
            }
        }
        model.addAttribute("pool", pool);
        model.addAttribute("fitness", fitness);
        model.addAttribute("gym", gym);
        model.addAttribute("massage", massage);
        model.addAttribute("sauna", sauna);
        return "club-card-update";
    }

    @PostMapping("/club-card-update/{id}")
    public String updateClubCard(@ModelAttribute("clubCard") ClubCard clubCard, BindingResult bindingResult, @PathVariable("id") Integer id,
                                 @ModelAttribute("customerId") Integer customerId, @ModelAttribute("attendanceModeId") Integer attendanceModeId,
                                 @ModelAttribute("pool") String pool, @ModelAttribute("fitness") String fitness,
                                 @ModelAttribute("gym") String gym, @ModelAttribute("massage") String massage, @ModelAttribute("sauna") String sauna) {
        if(bindingResult.hasErrors()) {
            return "club-card-update";
        }
        List<Integer> services = createServiceList(pool, fitness, gym, massage, sauna);
        clubCardService.updateClubCard(id, clubCard, customerId, attendanceModeId, services);
        return "redirect:/club-cards";
    }

    @GetMapping("/club-card-delete/{id}")
    public String deleteClubCard(@PathVariable("id") Integer id) {
        clubCardService.deleteClubCard(id);
        return "redirect:/club-cards";
    }

    private List<Integer> createServiceList(String pool, String fitness, String gym, String massage, String sauna) {
        List<Integer> services = new ArrayList<>();
        if(Objects.equals(pool, "on")) {
            services.add(1);
        }
        if(Objects.equals(fitness, "on")) {
            services.add(2);
        }
        if(Objects.equals(gym, "on")) {
            services.add(3);
        }
        if(Objects.equals(massage, "on")) {
            services.add(4);
        }
        if(Objects.equals(sauna, "on")) {
            services.add(5);
        }
        return services;
    }
}
