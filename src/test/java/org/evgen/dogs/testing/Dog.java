package org.evgen.dogs.testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dog {

    String name;
    Double weight;
    Double height;
    Date timeOfBirth;

    public Dog(String name, Double weight, Double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public Dog(String name, Double weight, Double height, String timeOfBirth) throws ParseException {
        this.name = name;
        this.weight = weight;
        this.height = height;

        SimpleDateFormat dateFormat = new SimpleDateFormat("uuuu-MM-dd'T'HH:mm:ss.SSSXXXX");
        Date date = dateFormat.parse(timeOfBirth);
        this.timeOfBirth = date;
    }

    public void setTimeOfBirth(Date timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
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

    public Date getTimeOfBirth() {
        return timeOfBirth;
    }
}
