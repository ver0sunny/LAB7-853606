import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Fuck {

    public static void main(String[] args) {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager
//                    .getConnection("jdbc:postgresql://localhost:5433/postgres",
//                            "postgres", "12345678");
//            System.out.println("Opened database successfully");

//            stmt = c.createStatement();
//            String sql = "CREATE TABLE COMPANY " +
//                    "(ID SERIAL PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " AGE            INT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " SALARY         REAL)";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();


//            stmt = c.createStatement();
//            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                    + "VALUES (DEFAULT, 'Allen', 25, 'Texas', 15000.00 ) RETURNING ID;";
//            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
//            ResultSet keys = stmt.getGeneratedKeys();
//            int id = 0;
//            if (keys != null && keys.next()) id = keys.getInt(1);
//            System.out.println(id);


//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                    + "VALUES (DEFAULT, 'Allen', 25, 'Texas', 15000.00 ) RETURNING ID;";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                    + "VALUES (DEFAULT, 'Teddy', 23, 'Norway', 20000.00 ) RETURNING ID;";
//            stmt.executeUpdate(sql);

//            stmt.close();
//            c.close();
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}
