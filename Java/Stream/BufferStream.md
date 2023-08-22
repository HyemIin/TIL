# 1. BufferStream
<img width="616" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/b8d5bb65-4a2f-4cd9-8ab6-fdd8d56ba31d">

- 위 그림에서보면, InputStreamReader가 브릿지 스트림이 된다.
- InputStream이 바이트 단위로 글자를 받고, InputStreamReader이 이를 인코딩한다.
- BufferedReader가 이제 글자를 단어로 모은다. (엔터 기준)

```java
public static void main(String[] args) throws IOException {
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        //버퍼 기능이 있는 문자 입력스트림
        BufferedReader br = new BufferedReader(isr);
        System.out.print("한글 입력");
        String line = br.readLine(); //라인 단위로 읽기
        System.out.println(line);
    }
```

```java
public static void main(String[] args) throws IOException {
//        InputStream is = System.in;
//        InputStreamReader isr = new InputStreamReader(is);
        //버퍼 기능이 있는 문자 입력스트림.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("한글 입력");
        String line = br.readLine(); //라인 단위로 읽기
        System.out.println(line);
    }
```

가장 많이쓰고 중요한...
## BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
- 이것이 키보드로 부터 라인 단위로 문자열을 입력받기 위한 스트림이다.
