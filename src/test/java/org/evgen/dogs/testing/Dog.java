package org.evgen.dogs.testing;


public class Dog {

    String name;
    double weight;
    double height;
    String timeOfBirth;


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

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public void setTimeOfBirth(String timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public String getTimeOfBirth() {

        return timeOfBirth;
    }


}
