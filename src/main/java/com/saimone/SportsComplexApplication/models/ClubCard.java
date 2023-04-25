package com.saimone.SportsComplexApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "club_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Поле обов'язкове для заповнення!")
    @Size(min = 8, max = 12, message = "Данні введено некоректно!")
    @Column(name = "issue_date", nullable = false)
    private String issueDate;

    @Column(name = "expire_date")
    private String expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id", nullable = false)
    private AttendanceMode attendanceMode;
}
