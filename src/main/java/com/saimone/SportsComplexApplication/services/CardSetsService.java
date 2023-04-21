package com.saimone.SportsComplexApplication.services;

import com.saimone.SportsComplexApplication.models.CardSets;
import com.saimone.SportsComplexApplication.models.ClubCard;
import com.saimone.SportsComplexApplication.models.Service;
import com.saimone.SportsComplexApplication.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CardSetsService {
    private final CardSetsRepo cardSetsRepo;

    @Autowired
    public CardSetsService(CardSetsRepo cardSetsRepo) {
        this.cardSetsRepo = cardSetsRepo;
    }

    public void changeSet(ClubCard updatedClubCard, List<Service> serviceList) {
        List<CardSets> delConnections = cardSetsRepo.findAllByClubCardId(updatedClubCard.getId());
        for(CardSets delCardSet : delConnections) {
            cardSetsRepo.deleteById(delCardSet.getId());
        }
        for(Service service : serviceList) {
            CardSets cardSets = new CardSets(updatedClubCard, service);
            cardSetsRepo.save(cardSets);
        }
    }
}
