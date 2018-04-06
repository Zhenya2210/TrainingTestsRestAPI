package org.evgen.dogs.testing;

import java.time.OffsetDateTime;


public class Dog {

    String name;
    double weight;
    double height;
    OffsetDateTime timeOfBirth;


    public Dog(String name, double weight, double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getName() {

        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setTimeOfBirth(OffsetDateTime timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public OffsetDateTime getTimeOfBirth() {

        return timeOfBirth;
    }


}
