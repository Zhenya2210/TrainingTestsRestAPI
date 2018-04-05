package org.evgen.dogs.testing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Dog {

    String name;
    Double weight;
    Double height;

    public Dog(String name, Double weight, Double height) {
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


}
