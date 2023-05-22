package com.example.giftcard.command.impl;

import com.example.giftcard.command.api.IssueCmd;
import com.example.giftcard.command.api.IssuedEvt;
import com.example.giftcard.command.api.RedeemCmd;
import com.example.giftcard.command.api.RedeemedEvt;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor//needed
@Slf4j
@Component
//@Profile("command")
public class Giftcard {
    @AggregateIdentifier
    private UUID id;
    private int remaningValue;

    //Issuing a new card means constructing new aggregate
    @CommandHandler
    public Giftcard(IssueCmd issueCmd){
        log.debug("handling issuedcmd {}  commandhandler", issueCmd);
        //amount in the command should be positive to accept
        if(issueCmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        apply(new IssuedEvt(issueCmd.getId(), issueCmd.getAmount()));
    }

    @CommandHandler
    public void handleReedemCmd(RedeemCmd reedemCmd){
        log.debug("handling reedemCmd {} commandhandler", reedemCmd);
        if(reedemCmd.getAmount() <= 0) throw new IllegalArgumentException("amount <) 0");
        if(reedemCmd.getAmount() > this.remaningValue) throw new IllegalArgumentException("amount > remaningValue");
        apply(new RedeemedEvt(reedemCmd.getId(), reedemCmd.getAmount()));

    }

    //command handlers make decisions. they don't change the sate.
    //becasue we are using event sourcing the only way the state can change  is through
    //an apply event and through the event handlers for those eventss.

    @EventSourcingHandler
    public void on (IssuedEvt issuedEvt){
        log.debug("applying issuedEvt {} eventsourcinghandler", issuedEvt);
        this.id = issuedEvt.getId();
        this.remaningValue = issuedEvt.getAmount();
    }

    @EventSourcingHandler
    public void on (RedeemedEvt redeemedEvt){
        log.debug("applying redeemedEvt {} eventsourcinghandler", redeemedEvt);
        this.id = redeemedEvt.getId();
        this.remaningValue = redeemedEvt.getAmount();
    }


}
