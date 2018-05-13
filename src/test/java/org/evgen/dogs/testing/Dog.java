package org.evgen.dogs.testing;


public class Dog {

    private String name;
    private double weight;
    private double height;
    private String timeOfBirth;


    public Dog(String name, double weight, double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;

    }

    public Dog(String name, double weight, double height, String timeOfBirth) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.timeOfBirth = timeOfBirth;
    }


    public String getName() {

        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getTimeOfBirth() {

        return timeOfBirth;
    }


}
