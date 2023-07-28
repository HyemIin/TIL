# 질문
부모-자식 클래스 관계에서, 자식 클래스 인스턴스를 부모 클래스 타입으로 형변환하는 것(업캐스팅)이 가능한데, 업캐스팅이 필요한 이유가 궁금합니다.
업캐스팅이 반드시 필요한 상황이나 이유가 있을까요?? 업캐스팅을 하지 않으면 무엇이 불편할까요??
설명의 편의를 위해 코드를 아래 첨부 드립니다.
[질문을 하게 된 계기]
(아래 코드를 예시로) Person p1 = new Dancer(); 로 객체를 생성하면, Dancer 클래스의 메서드인 dance() 를 사용하지 못하는데(p1.dance() 불가능).. 굳이 업캐스팅 할 필요가 있는지? 오히려 사용할 수 있는 메서드 폭을 제한시키는게 아닌지? 즉 업캐스팅을 하면 더 불편해지는 것이 아닌가?라고 생각이 들었기 때문입니다.
제가 공부해봤을 땐,  아래 study 메서드가 매개변수로 부모 클래스를 받을 경우, 부모 클래스로 업캐스팅된 인스턴스를 모두 활용할 수 있기 때문에 편리하다. 라고 배웠습니다. 그렇지 않으면 study 메서드가 받을 수 있는 매개변수 타입을 전부 고려하여 오버로딩해야하는 불편함이 있기 때문이라 합니다. 하지만 실제론 study 메서드가 매개변수로 부모 클래스인 Person을 받는다 해도, 자식 클래스의 타입으로 선언된 객체(p2)까지 문제 없이 받을 수 있는 걸 보곤,,의문이 풀리지 않았습니다.

public class Main {
    public static void main(String[] args) {
        Person p1 = new Dancer("박종혁",27);
        Dancer p2 = new Dancer("김철수",25);
        University university = new University();
        university.study(p1);
        university.study(p2); // 이게 된다구..?
    }
}

class Person {
    String name;
    int age;
    public void speak() {
        System.out.println(name + ": 안녕하세요");
    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
class Dancer extends Person{
    public void dance() {
        System.out.println(name + ": 춤을 춥니다");
    }
    public Dancer(String name, int age) {
        super(name, age);
    }


}
class University {
    public void study(Person person) {
        System.out.println(person.name+"님은 공부 중입니다.");
    }
}

# 답변
(배경) 상황에 따라 ArrayList의 장점이 필요한 경우, LinkedList의 장점이 필요한 경우가 각각 다르다. 그 다른 상황에 맞춰 적합한 구현체를 선택하여 구현할 수 있다.
(방법) 단, 업캐스팅으로 부모 타입의 메서드를 사용하여, 타입을 통일시킨다.
(목적) 왜냐하면 ArrayList & LinkedList 구현체가 둘 다 조상 인터페이스인 List에서 선언한 메서드만 활용하여 통일되게 동작하도록 희망하기 때문이다.
