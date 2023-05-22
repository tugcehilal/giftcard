package com.example.giftcard.query;

import com.example.giftcard.command.api.IssuedEvt;
import com.example.giftcard.command.api.RedeemedEvt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@Slf4j
//@Profile("query")
public class SummaryProjector {

    private final EntityManager entityManager;

    @EventHandler
    public void on(IssuedEvt issuedEvt){
        log.debug("projecting issuedEvt {}", issuedEvt);
        entityManager.persist(new GiftCardSummary( issuedEvt.getId(), issuedEvt.getAmount(), issuedEvt.getAmount()));
    }

    @EventHandler
    public void on(RedeemedEvt redeemedEvt){
        log.debug("projecting reedemdevt{}", redeemedEvt);
        entityManager.find(GiftCardSummary.class, redeemedEvt.getId()).remainingValue -= redeemedEvt.getAmount();
    }

    @QueryHandler
    public GiftCardSummary handle(GiftCardSummaryQuery qry) {
        return entityManager.find(GiftCardSummary.class, qry.getId());
    }
}
