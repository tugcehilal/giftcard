package com.example.giftcard.query;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Value
public class GiftCardSummaryQuery {
    @Id
    UUID id;
}
