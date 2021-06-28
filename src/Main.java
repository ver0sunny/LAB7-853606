import Common.collectionInfo.*;
import Common.exceptions.NoSuchStudentsCountException;

import javax.print.DocFlavor;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main( String args[] ) {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                            "postgres", "12345678");
            System.out.println("Opened database successfully");


//            stmt = c.createStatement();
//            String sql = "CREATE TABLE USERS " +
//                    "(USER_NAME  TEXT    NOT NULL, " +
//                    "USER_PASSWORD  TEXT    NOT NULL)";
//            stmt.executeUpdate(sql);
//            stmt.close();

//            stmt = c.createStatement();
//            String sql = "INSERT INTO USERS (USER_NAME,USER_PASSWORD) VALUES ('gg',2)";
//            stmt.executeUpdate(sql);
//            stmt.close();


//            stmt = c.createStatement();
//            String sql = "DELETE from USERS where USER_NAME = 'yolo'";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();

//            stmt = c.createStatement();
//            String sql = "CREATE TABLE STUDENT_GROUPS " +
//                    "(ID SERIAL PRIMARY KEY     NOT NULL," +
//                    " USER_NAME  TEXT    NOT NULL, " +
//                    " GROUP_NAME           TEXT    NOT NULL, " +
//                    " CREATION_DATE            TEXT     NOT NULL, " +
//                    " ADMIN_NAME            TEXT     NOT NULL, " +
//                    " ADMIN_HEIGHT            INT     NOT NULL, " +
//                    " ADMIN_WEIGHT            INT     NOT NULL, " +
//                    " ADMIN_PASSPORT_ID            TEXT     NOT NULL, " +
//                    " X_COORDINATE            TEXT     NOT NULL, " +
//                    " Y_COORDINATE            TEXT     NOT NULL, " +
//                    " STUDENTS_COUNT            INT     NOT NULL, " +
//                    " SHOULD_BE_EXPELLED            INT     NOT NULL, " +
//                    " FORM_OF_EDUCATION            TEXT     NOT NULL, " +
//                    " SEMESTER            TEXT     NOT NULL)";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();


            stmt = c.createStatement();
            String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


//            String sql = "INSERT INTO STUDENT_GROUPS (ID,USER_NAME,GROUP_NAME,CREATION_DATE,ADMIN_NAME,ADMIN_HEIGHT,ADMIN_WEIGHT,ADMIN_PASSPORT_ID,X_COORDINATE,Y_COORDINATE,STUDENTS_COUNT,SHOULD_BE_EXPELLED,FORM_OF_EDUCATION,SEMESTER) "
//                    + "VALUES (DEFAULT, 'mimi', 'R3142', '" + localDateTime + "'," +  " 'Voron', 175, 60, '2334786542', 43, 12, 17, 5, 'FULL_TIME_EDUCATION', 'SECOND' )";
//            System.out.println(stmt.executeUpdate(sql));

//            String sql = "INSERT INTO STUDENT_GROUPS (ID,USER_NAME,GROUP_NAME,CREATION_DATE,ADMIN_NAME,ADMIN_HEIGHT,ADMIN_WEIGHT,ADMIN_PASSPORT_ID,X_COORDINATE,Y_COORDINATE,STUDENTS_COUNT,SHOULD_BE_EXPELLED,FORM_OF_EDUCATION,SEMESTER) "
//                    + "VALUES (DEFAULT, 'mimi', 'R3136', '" + localDateTime + "'," +  " 'Totoro', 100, 100, '12345678910', 100, 100, 100, 100, 'DISTANCE_EDUCATION', 'FOURTH' );";
//            stmt.executeUpdate(sql);

//            String sql = "INSERT INTO STUDENT_GROUPS (ID,USER_NAME,GROUP_NAME,CREATION_DATE,ADMIN_NAME,ADMIN_HEIGHT,ADMIN_WEIGHT,ADMIN_PASSPORT_ID,X_COORDINATE,Y_COORDINATE,STUDENTS_COUNT,SHOULD_BE_EXPELLED,FORM_OF_EDUCATION,SEMESTER) "
//                    + "VALUES (DEFAULT, 'mimi', 'R3140', '" + localDateTime + "'," +  " 'Layzveb', 180, 63, '9876543211', 43, 11, 33, 13, 'EVENING_CLASSES', 'FIFTH' )";
//            System.out.println(stmt.executeUpdate(sql));
//            stmt.close();
//            c.close();


//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM STUDENT_GROUPS;" );
//            while ( rs.next() ) {
//                Integer id = rs.getInt("id");
//                String  groupName = rs.getString("group_name");
//                LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creation_date"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                String adminName = rs.getString("admin_name");
//                int height = rs.getInt("admin_height");
//                int weight = rs.getInt("admin_weight");
//                String adminPassportId = rs.getString("admin_passport_id");
//                float x = rs.getFloat("x_coordinate");
//                long y = rs.getLong("y_coordinate");
//                int studentsCount = rs.getInt("students_count");
//                int shouldBeExpelled = rs.getInt("should_be_expelled");
//                FormOfEducation formOfEducation = FormOfEducation.valueOf(rs.getString("form_of_education"));
//                Semester semester = Semester.valueOf(rs.getString("semester"));
//                Person admin = new Person(adminName,height,weight,adminPassportId);
//                Coordinates coordinates= new Coordinates(x,y);
//                StudyGroup studyGroup = new StudyGroup(id,groupName,creationDate,admin,coordinates,studentsCount,shouldBeExpelled,formOfEducation,semester);
//                System.out.println(studyGroup);
//
//
//
//            }
//            rs.close();
//            stmt.close();
//            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}