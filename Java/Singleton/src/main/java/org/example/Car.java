package org.example;

public class Car {

    private static int SerialNum = 10000;
    private int CarNum;

    public Car() {
        SerialNum++;
        CarNum = SerialNum;
    }

    public int getCarNum() {
        return CarNum;
    }
}
