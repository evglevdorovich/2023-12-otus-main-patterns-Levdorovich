package com.example.spaceship.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Fuel {

    @Setter(AccessLevel.NONE)
    private int amount;

    public void decrease(int amountToDecrease) {
        amount -= amountToDecrease;
    }
}
