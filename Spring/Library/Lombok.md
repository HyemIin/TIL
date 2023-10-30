# 1. AllArgsConstructor vs RequiredArgsConstructor
## 1) AllArgsConstructor
- 모든 필드에 대한 생성자 생성
## 2) RequiredArgsConstructor
- 초기화가 안된 필드 & Notnull이 붙은 필드를 생성자로 생성
## 3) 생성자 어노테이션의 문제점
- @AllArgsConstructor 어노테이션은 생성자를 생성할 때, <br> class 내부에 선언된 field의 순서인 cancelPrice, orderPrice 순으로 생성자 파라미터를 생성한다.
- 이처럼 필드 위치가 바뀌어도 어노테이션이 그걸 잡아주지 않는다.

```
@AllArgsConstructor
public class Order {
    private int cancelAmount;
    private int orderAmount;
}


// 취소수량 4개, 주문수량 5개
Order order = new Order(4, 5);
```

- 즉, 위 코드에서 cancleAmount와 orderAmount 위치를 바꿔도 롬복어노테이션이 반영하지 않는다.
- 위치를 바꿨다고 바꾼대로 값을 입력할 경우 대참사 발생
- 또한, 해당 클래스를 상속하는 경우 생성자 어노테이션이 반영 안된다.
