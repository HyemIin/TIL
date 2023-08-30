public class BookDAO implements BookRepository{
    private Connection connection;
    private Statement statement; // 잘 안씀
    private PreparedStatement preparedStatement; // SQL 문장에 파라미터가 있는 경우에 사용
    private ResultSet resultSet;

    //DB 연결
    public void getConnection() {
        String url = "jdbc:mysql://localhost:3306/fcampus"; // 프로토콜://mysql 설치서버ip : 포트번호(3306이 디폴트)
        String username = "root";
        String password = "wjdrk1004";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 저장 기능(Insert)
    public int bookRegister(Book book) {
        String SQL = "insert into tblbook(title,company,name,price) values(?,?,?,?)";
        getConnection();
        int cnt = 1;
        try {
            preparedStatement=connection.prepareStatement(SQL); // 미완성 SQL이지만 미리 컴파일 시키는 이유는, 이 SQL이 반복될 경우 SQL form을 중복하여 활용하기 위함.(그래서 -ed 과거형으로 표현)
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2,book.getCompany());
            preparedStatement.setString(3,book.getName());
            preparedStatement.setInt(4,book.getPrice());
            cnt = preparedStatement.executeUpdate(); // 괄호 안에 SQL 안넣음

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbclose();
        }
        return cnt;
    }

    // Select 기능
    public List<Book> bookList() {
        String SQL = "select * from tblbook";
        getConnection();
        List<Book> blist = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // getInt(),getString() : 어떤 컬럼을 가져 올 것인지
                int num =  resultSet.getInt("num");
                String title = resultSet.getString("title");
                String company = resultSet.getString("company");
                String name = resultSet.getString("name");
                int price =  resultSet.getInt("price");
                // 데이터화 시키기 위해서 데이터를 묶고(VO), 담아줘야함(List)
                Book book = new Book(num, title, company, name, price);
                blist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbclose();
        }
        return blist;
    }
    // 특정 문자가 포함된 것만 가져오는 기능
    @Override
    public List<Book> getLikeBooks(String search) {
        String SQL = "select * from tblbook where title like ?";
        getConnection();
        List<Book> blist = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // getInt(),getString() : 어떤 컬럼을 가져 올 것인지
                int num =  resultSet.getInt("num");
                String title = resultSet.getString("title");
                String company = resultSet.getString("company");
                String name = resultSet.getString("name");
                int price =  resultSet.getInt("price");
                // 데이터화 시키기 위해서 데이터를 묶고(VO), 담아줘야함(List)
                Book book = new Book(num, title, company, name, price);
                blist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbclose();
        }
        return blist;
    }
    // 특정 레코드 1개만 조회하는 기능
    public Book getByNum(int num) {
        Book book = null;
        String SQL = "Select * from tblbook where num =?";
        getConnection();


        try {
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                num = resultSet.getInt("num");
                String title = resultSet.getString("title");
                String company = resultSet.getString("company");
                String name = resultSet.getString("name");
                int price =  resultSet.getInt("price");
                // 묶기(vo)
                book = new Book(num, title, company, name, price);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbclose();
        }
        return book;
    }

    //update 기능
    public int bookUpdate(Book vo) {
        String SQL = "update tblbook set company=?,name=?,price=? where num=?";
        int cnt = -1;
        getConnection();
        try {
            preparedStatement= connection.prepareStatement(SQL);
            preparedStatement.setString(1,vo.getCompany());
            preparedStatement.setString(2, vo.getName());
            preparedStatement.setInt(3, vo.getPrice());
            preparedStatement.setInt(4, vo.getNum());
            cnt = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbclose();
        }
        return cnt;
    }

    // 삭제 기능
    @Override
    public int bookDelete(int num) {
        String SQL = "delete from tblbook where num=?";
        int cnt = -1;
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, num);
            cnt = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbclose();
        }
        return cnt;
    }



    public void dbclose() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
