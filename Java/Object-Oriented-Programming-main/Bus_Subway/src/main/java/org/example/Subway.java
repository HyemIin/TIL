package org.example;

public class Subway {
    int SubwayNumber;
    int passengerCount;
    int money;

    public Subway(int subwayNumber) {
        SubwayNumber = subwayNumber;
    }

    public void take(int money) {
        this.money += money;
        passengerCount++;
    }

    public void showSubwayinfo() {
        System.out.println(SubwayNumber+"번의 승객 수는 " + passengerCount +"명 이고, 수입은 "+money+"원 입니다.");
    }
}
