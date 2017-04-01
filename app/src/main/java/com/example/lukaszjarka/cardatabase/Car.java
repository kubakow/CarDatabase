package com.example.lukaszjarka.cardatabase;

public class Car {

    private String make;
    private String model;
    private String image;
    private int year;

    public Car(String make, String model, String image, int year) {
        this.make = make;
        this.model = model;
        this.image = image;
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getImage() {
        return image;
    }

    public int getYear() {
        return year;
    }
}
