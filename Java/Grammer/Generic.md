# 1. 제네릭 프로그래밍(너 T야??)

- 제네릭도 자료형이다. T라고 쓴다.
- 단, 자료형인데, 뭐든 다 받을 수 있는 포용력이 넓은 자료형이다.
- (배경) 여러 타입을 포용하기 위해 Object 클래스를 활용한다면, 해당 값을 불러올 때(get) 매번 형변환이 필요하다. 이는 곧 확장성 측면에서도 문제가 있다. 즉, 불편함. 아래 예시를 보자

```java
public class Powder {

	public String toString() {
		return "재료는 Powder 입니다";
	}
}

```

```java
public class ThreeDPrinter{

	private Object material;

	public void setMaterial(Object material) {
		this.material = material;
	}

	public Object getMaterial() {
		return material;
	}
}

```

```java
// 메인 메서드에서 실행
ThreeDPrinter printer = new ThreeDPrinter();

Powder powder = new Powder();
printer.setMaterial(powder);

Powder p = (Powder)printer.getMaterial(); //여기서 형변환 필요
// getter의 return 타입이 object 클래스이기 때문에, 다운캐스팅을 해줘야한다.
```

- 그럼 확장성 측면에서는 어떤 문제가 있나?

```java
public class Main {

    public static void main(String[] args) {
        ThreeDPrinter printer = new ThreeDPrinter();
        ThreeDPrinter st_printer = new ThreeDPrinter();
        Powder powder = new Powder();
        printer.setMaterial(powder);
        st_printer.setMaterial("가나다");

        Powder p = (Powder)printer.getMaterial(); //여기서 형변환 필요
        // getter의 return 타입이 object 클래스이기 때문에, 다운캐스팅을 해줘야한다.
        
				//으어어 새로운 타입 생길때마다 계속 만들어줘야해ㅐㅇ애ㅐㅐ 불편해
				String s = (String)st_printer.getMaterial(); //여길 봐야한다..!
				
        System.out.println(p);
        System.out.println(s);
    }
}
```

- (정의) 클래스 내부에서 사용할 데이터 타입을 외부에서 지정하는 기법으로, 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입체를 해주는 기능이다.

- 위 예시에서 Object → T로 변경했다.

```java
public class ThreeDPrinter<T>{

    private T material;

    public void setMaterial(T material) {
        this.material = material;
    }
    public T getMaterial() {
        return material;
    }
}
```

```java
public static void main(String[] args) {
        ThreeDPrinter<Powder> printer = new ThreeDPrinter(); //여기서 미리 타입 정해줄 것
        ThreeDPrinter<String> st_printer = new ThreeDPrinter();
        Powder powder = new Powder();
        printer.setMaterial(powder);
        st_printer.setMaterial("가나다");

        Powder p = printer.getMaterial(); // 캐스팅 불필요
        String s = st_printer.getMaterial(); // 22222

        System.out.println(p);
        System.out.println(s);
    }
```

- 위 코드를 보면, 객체 생성 시 사용하고자하는 타입을 명시함으로써, 컴파일 시점에 검사가 가능하다. 즉 불필요한 에러를 방지하는 효과가 있다.
- 이렇게 타입이 정해져있기 때문에 타입 변환을 할 필요가 없어서 프로그램 성능 향상 효과를 얻을 수 있다.
