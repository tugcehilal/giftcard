package com.example.giftcard.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardSummary {
    @Id
    UUID id;
    int remainingValue;
    int initialValue;

}
