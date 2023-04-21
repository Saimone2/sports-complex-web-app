package com.saimone.SportsComplexApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardSets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private ClubCard clubCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public CardSets(ClubCard clubCard, Service service) {
        this.clubCard = clubCard;
        this.service = service;
    }
}
