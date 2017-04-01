package com.example.lukaszjarka.cardatabase;

public class CarBuilder {
    private String make;
    private String model;
    private String image;
    private int year;

    public CarBuilder setMake(String make) {
        this.make = make;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setImage(String image) {
        this.image = image;
        return this;
    }

    public CarBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public Car createCar() {
        return new Car(make, model, image, year);
    }
}