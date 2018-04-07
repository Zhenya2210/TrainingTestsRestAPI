package org.evgen.dogs.testing;

import java.time.OffsetDateTime;


public class Dog {

    String name;
    String weight;
    String height;
    OffsetDateTime timeOfBirth;


    public Dog(String name, String weight, String height) {
        this.name = name;
        this.weight = weight;
        this.height = height;

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {

        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public void setTimeOfBirth(OffsetDateTime timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public OffsetDateTime getTimeOfBirth() {

        return timeOfBirth;
    }


}
