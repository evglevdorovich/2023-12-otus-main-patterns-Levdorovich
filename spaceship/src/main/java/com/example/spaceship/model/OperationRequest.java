package com.example.spaceship.model;

import lombok.Value;

@Value
public class OperationRequest {
    String action;
    Object[] args;
}
