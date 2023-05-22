package com.example.giftcard.command.api;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class IssueCmd {
   //Commands always have some target aggregate
   @TargetAggregateIdentifier
    UUID id;
    int amount;
}
