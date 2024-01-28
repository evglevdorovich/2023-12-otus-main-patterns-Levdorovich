package com.example.spaceship.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spaceship implements Movable, Rotatable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @Setter
    @AttributeOverride(name = "coordinates", column = @Column(name = "position_coordinates"))
    private Vector position;

    @Embedded
    @AttributeOverride(name = "coordinates", column = @Column(name = "velocity_coordinates"))
    private Vector velocity;

    @Setter
    private int direction;

    private int directionsNumber;

    private int angularVelocity;
}
