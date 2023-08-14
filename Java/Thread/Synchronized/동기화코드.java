class Bank {
    private int money = 10000;

    public synchronized void saveMoney(int save) { //synchronized를 사용하지 않을 시, 돈이 저축되는 과정이 뒤죽박죽 섞인다.
        int m = this.getMoney();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setMoney(m+save);

    }

    public synchronized void minusMoney(int minus) {
        int m = this.money;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setMoney(m-minus);

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

class Parents extends Thread {
    @Override
    public void run() {
        System.out.println("start save");
        SyncMain.mybank.saveMoney(3000);
        System.out.println("현재 저축 금액 : "+SyncMain.mybank.getMoney());

    }
}

class Son extends Thread {
    @Override
    public void run() {
        System.out.println("start minus");
        SyncMain.mybank.minusMoney(1000);
        System.out.println("돈이 차감 되었습니다.");
        System.out.println("현재 저축 금액 : "+SyncMain.mybank.getMoney());
    }

}

public class SyncMain {
    public static Bank mybank = new Bank();

    public static void main(String[] args) throws InterruptedException {
        Parents parents = new Parents();
        parents.start();

        Thread.sleep(200);

        Son son = new Son();
        son.start();
    }
  // result : 13000원 -> 12000원 되야함
}
