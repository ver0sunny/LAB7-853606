package Server;

import Common.collectionInfo.*;
import Common.exceptions.CollectionIsEmptyException;
import Common.exceptions.NoSuchStudentsCountException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

public class CollectionManager {
    private LinkedList<StudyGroup> studyGroupsCollection = new LinkedList<>();
    private LocalDateTime creationTime;
    private LinkedList<StudyGroup> studyGroupsAddedThisSession = new LinkedList<>();


    public CollectionManager() {
        this.studyGroupsCollection = loadCollection();
    }

    public LinkedList<StudyGroup> getCollection() {
        return studyGroupsCollection;
    }

    public int getCollectionSize() {
        return studyGroupsCollection.size();
    }

    // sorts collection starting with the tallest admin
    public void sortCollection() {
        studyGroupsCollection.sort(StudyGroup::compareTo);
    }

    public void addToCollection(StudyGroup studyGroup) {
        studyGroupsCollection.add(studyGroup);
        studyGroupsAddedThisSession.add(studyGroup);
    }

    public void clearCollection() {
        LinkedList<StudyGroup> updatedList = loadCollection();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                    "postgres", "12345678");

            stmt = c.createStatement();
            String sql = "DELETE from STUDENT_GROUPS where USER_NAME = '" + System.getProperty("username") + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void saveCollection() {
        Connection c = null;
        Statement stmt = null;
        for (StudyGroup studyGroup : studyGroupsAddedThisSession) {
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                        "postgres", "12345678");
                stmt = c.createStatement();
                String sql = "INSERT INTO STUDENT_GROUPS (ID,USER_NAME,GROUP_NAME,CREATION_DATE,ADMIN_NAME,ADMIN_HEIGHT,ADMIN_WEIGHT,ADMIN_PASSPORT_ID,X_COORDINATE,Y_COORDINATE,STUDENTS_COUNT,SHOULD_BE_EXPELLED,FORM_OF_EDUCATION,SEMESTER) "
                    + "VALUES (DEFAULT, '" + System.getProperty("username") + "', '" +  studyGroup.getName() + "', '" +
                        studyGroup.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "', '" +
                        studyGroup.getGroupAdmin().getName() + "', '" + studyGroup.getGroupAdmin().getHeight() + "', '" +
                        studyGroup.getGroupAdmin().getWeight() + "', '" + studyGroup.getGroupAdmin().getPassportID() + "', '" +
                        studyGroup.getCoordinates().getX() + "', '" + studyGroup.getCoordinates().getY() + "', '" +
                        studyGroup.getStudentsCount() + "', '" + studyGroup.getShouldBeExpelled() + "', '" +
                        studyGroup.getFormOfEducation() + "', '" + studyGroup.getSemester() + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            studyGroupsCollection = loadCollection();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public LinkedList<StudyGroup> loadCollection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                    "postgres", "12345678");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENT_GROUPS;");
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String groupName = rs.getString("group_name");
                LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creation_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String adminName = rs.getString("admin_name");
                int height = rs.getInt("admin_height");
                int weight = rs.getInt("admin_weight");
                String adminPassportId = rs.getString("admin_passport_id");
                float x = rs.getFloat("x_coordinate");
                long y = rs.getLong("y_coordinate");
                int studentsCount = rs.getInt("students_count");
                int shouldBeExpelled = rs.getInt("should_be_expelled");
                FormOfEducation formOfEducation = FormOfEducation.valueOf(rs.getString("form_of_education"));
                Semester semester = Semester.valueOf(rs.getString("semester"));
                Person admin = new Person(adminName, height, weight, adminPassportId);
                Coordinates coordinates = new Coordinates(x, y);
                StudyGroup studyGroup = new StudyGroup(id, groupName, creationDate, admin, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semester);
                studyGroupsCollection.add(studyGroup);
            }
            rs.close();
            stmt.close();
            c.close();
            return studyGroupsCollection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void remove(StudyGroup studyGroup) {
        studyGroupsCollection.remove(studyGroup);
    }

    public Person createAdmin(String name, Integer height, Integer weight, String passportId) {
        Person admin = new Person(name, height, weight, passportId);
        return admin;
    }

    public Coordinates createCoordinates(float x, long y) {
        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    public StudyGroup getFirst() {
        if (studyGroupsCollection.isEmpty()) return null;
        return studyGroupsCollection.getFirst();
    }

    public StudyGroup getLast() {
        if (studyGroupsCollection.isEmpty()) return null;
        return studyGroupsCollection.getLast();
    }

    public StudyGroup getById(Integer id) {
        if (studyGroupsCollection.isEmpty()) return null;
        for (StudyGroup studyGroup : studyGroupsCollection) {
            if (studyGroup.getId().equals(id)) return studyGroup;
        }
        return null;
    }

    public Integer generateId() {
        if (studyGroupsCollection.isEmpty()) return 1;
        return studyGroupsCollection.getLast().getId() + 1;
    }

    public void updateAllId() {
        int i = 0;
        for (StudyGroup studyGroup : studyGroupsCollection) {
            studyGroup.setId(i + 1);
        }
    }

//    public void insertAt(Integer id) {
//        for (StudyGroup studyGroup : studyGroupsCollection) {
//            if (studyGroup.getId() == id) {
//
//            }
//        }
//    }

    public void removeById(Integer id) {
        try {
            if (studyGroupsCollection.isEmpty()) throw new CollectionIsEmptyException();
            studyGroupsCollection.removeIf(studyGroup -> studyGroup.getId().equals(id));
        } catch (CollectionIsEmptyException e) {
            ServerConsole.printerror("Collection is empty");
        }
    }


    public void removeByStudentsCount(int studyCount) {
        try {
            int theGroupsCount = 0;
            if (studyGroupsCollection.isEmpty()) throw new CollectionIsEmptyException();
            for (StudyGroup studyGroup : studyGroupsCollection) {
                if (studyGroup.getStudentsCount() == studyCount) {
                    theGroupsCount += 1;
                }
            }
            if (theGroupsCount == 0) throw new NoSuchStudentsCountException();
            studyGroupsCollection.removeIf(studyGroup -> studyGroup.getStudentsCount() == studyCount);
        } catch (CollectionIsEmptyException e) {
            ServerConsole.printerror("Collection is empty T-T");
        } catch (NoSuchStudentsCountException e) {
            ServerConsole.printerror("No groups with this many students found");
        }
    }

    public LinkedList<StudyGroup> greaterThanByFormOfEducation(FormOfEducation formOfEducation) throws CollectionIsEmptyException {
        if (studyGroupsCollection.isEmpty()) throw new CollectionIsEmptyException();
        LinkedList<StudyGroup> linkedList = new LinkedList<>();
        for (StudyGroup studyGroup : studyGroupsCollection) {
            if (studyGroup.getFormOfEducation().compareTo(formOfEducation) > 0) {
                linkedList.add(studyGroup);
                return linkedList;
            }
        }
        return null;
    }

    class SortDecendBySemester implements Comparator<StudyGroup> {
        public int compare(StudyGroup sG1, StudyGroup sG2) {
            if (sG1.getSemester() == sG2.getSemester()) {
                return 0;
            } else if (sG1.getSemester().compareTo(sG2.getSemester()) < 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public String decendBySemester() {
        studyGroupsCollection.sort(new SortDecendBySemester());
        return studyGroupsCollection.toString();
    }

    public String toString() {
        if (studyGroupsCollection.isEmpty()) {
            return "This collection is empty (｡•́︿•̀｡)";
        }
        StringBuilder info = new StringBuilder();
        for (StudyGroup studyGroup : studyGroupsCollection) {
            info.append(studyGroup).append("\n");
        }
        return info.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionManager that = (CollectionManager) o;
        return Objects.equals(studyGroupsCollection, that.studyGroupsCollection) &&
                Objects.equals(creationTime, that.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyGroupsCollection, creationTime);
    }
}

