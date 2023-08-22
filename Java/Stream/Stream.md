# 1. Stream

- 스트림은 빨대다..
- (정의) 자바에서는 파일이나 콘솔의 입출력을 직접 다루지 않고, 스트림(stream)이라는 흐름을 통해 다룹니다.  즉, 스트림은 운영체제에 의해 생성되는 가상의 연결 고리를 의미하며, **중간 매개자** 역할을 합니다.

<img width="590" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/a951e229-b4db-43d7-a62c-ff5035b27182">

- 자바에서는 모든 스트림을 클래스로 만들어 놓음(io package내 존재)
- (why?) 그냥 입출력하면 되지 왜 스트림을 거치냐?? 입출력하고자 하는 데이터의 특징에 맞춰 더 효율적으로 입출력하기 위함
- (wht not?) 스트림을 안쓰면 뭐가 불편한데?? 외부 파일 같은거 어케 불러올것인지? 문자 인코딩 같은 부분도 개발자가 하나하나 해줘야함
    
    [데이터 종류에 따른 분류]
    
    - 바이트 스트림
        - InputStream : 바이트 단위 읽기 (read() )
        - OutputStream : 바이트 단위 쓰기
        - (중요) **FileInputStream**
        - (중요) **FileOutputStream**
    - 문자 스트림 : 문자데이터를 처리하는 스트림(스트림 내부에서 인코딩 진행)
        - Reader : 문자 단위 읽기
        - Writer : 문자단위 쓰기 (write(), print(),println() )
        - (중요) **FileReader**
        - (중요) **FileWriter**
        - (중요) InputStreamReader,Writer
    
    [처리 방식에 따른 분류]
    
    - 노드 스트림 : 입출력 데이터의 가장 먼저 연결되는 스트림
    - 필터 스트림 : 단독사용불가 → 노드 스트림과 연결해서 사용. 노드 스트림의 성능을 키우기 위함
    → 생성자를 통해 연결
        - 버퍼기능이 있는 스트림 
        BufferdFileInpitStream,BufferedFileOutputStream,BufferedReader,BufferedWriter
        위 처럼 입출력 성능을 개선시키는 용도
        - 브릿지스트림 : 바이트입출력스트림을 문자입출력스트림으로 변환
        
    
    ```java
    InputStream is = Ststem.in;
    try{
    		int data = is.read();
    		sout(char)data);
    		data = is.read();
    		sout(char)data);sout
    } catch(Exception e)
    
    	//A 입력할 경우, 65,10이 나온다. 엔터도 개행문자로 인지하기 때문(엔터 = 10)
    ```
    
- InputStream
    - read()메서드는 1byte씩 데이터를 읽는다. 만약 읽을 데이터가 없을 경우, -1을 return 한다.
    - 그렇기 때문에 한글을 입력할 수 없다.
- InputStreamReader
    - 이걸 쓰면 문자단위로 input하기 때문에 한글도 읽을 수 있다.

```java
public static void main(String[] args) throws IOException {
//        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(System.in); // 스트림 연결-인코딩 진행
        System.out.println("한글 입력");
        int data;
        while ((data = isr.read()) != -1) {
            System.out.print((char) data);
        }
        
    }
```
