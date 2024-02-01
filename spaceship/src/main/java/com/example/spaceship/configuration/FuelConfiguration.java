package com.example.spaceship.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FuelConfiguration {
    private final int fuelAmountConsumption;

    public FuelConfiguration(@Value("${fuel.amount-consumption}") int fuelAmountConsumption) {
        this.fuelAmountConsumption = fuelAmountConsumption;
    }
}
