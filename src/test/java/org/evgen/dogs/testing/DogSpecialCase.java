package org.evgen.dogs.testing;

public class DogSpecialCase {

    String name;
    String weight;
    String height;
    String timeOfBirth;

    public DogSpecialCase(String name, String weight, String height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public DogSpecialCase(String name, String weight, String height, String timeOfBirth) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.timeOfBirth = timeOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(String timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }
}
