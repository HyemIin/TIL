package org.example;

public class Bus {

    int busNumber;
    int passengerCount;
    int money;

    public Bus(int busNumber) {
        this.busNumber = busNumber;
    }

    public void take(int money) {
        this.money += money;
        passengerCount++;
    }

    public void showBusinfo() {
        System.out.println(busNumber+"번의 승객 수는 " + passengerCount +"명 이고, 수입은 "+money+"원 입니다.");
    }
}
