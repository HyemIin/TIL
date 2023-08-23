# 2. Gson

gson 라이브러리 활용하는 법에 대해 알아보자

(정의) Gson은 Java에서 Json을 파싱하고, 생성하기 위해 사용되는 구글에서 개발한 오픈소스.

(배경) json 타입에서 원하는 데이터만 골라 읽어오는 건 어렵고 귀찮다. JsonObject,JsonArray의 반복이 필요하기 때문

- Gson 객체 생성 방법
    - Gson gson = new GsonBuilder().setPrettyPrinting().create()
    - Gson gson = new GsonBuilder().create()
    - Gson gson = new Gson()


그렇기 때문에 아래 방법처럼 꺼내서 쓸 수 있다.

```java
try{
            FileReader fileReader = new FileReader("input2.json");
            Gson gson = new Gson();
            Booklist booklist = gson.fromJson(fileReader, Booklist.class);
            fileReader.close();
            List<Book> books = booklist.getBooks();
            for (Book book : books) {
                System.out.println(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
```

Gson을 활용하면 Java Object를 Json 문자열로 변환할 수 있고, Json 문자열을 Java Object로 변환할 수 있다.

- 자바에서 Json 생성하는 방법
  - JsonObject jsonObject = new JsonObject();
  - JsonObject.addProperty(”key1”,”value1”)
  - String JsonStr = gson.tojson(jsonObject)
  
- Json 형변환
    - Student student = gson.fromJson(jsonStr, Student.class);
