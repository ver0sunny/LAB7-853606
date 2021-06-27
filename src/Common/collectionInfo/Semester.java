package Common.collectionInfo;

import java.io.Serializable;

public enum Semester implements Serializable {
    DEFAULT,
    SECOND,
    FOURTH,
    FIFTH,
    SEVENTH,
    EIGHTH;

    public static String listAll() {
        StringBuilder listAll = new StringBuilder();
        for (Semester semester : values()) {
            listAll.append(semester.name()).append(" ");
        }
        String[] split = listAll.toString().split(" ");
        return new StringBuilder().append(split[1]).append(" ").append(split[2]).append(" ").append(split[3]).append(" ").append(split[4]).append(" ").append(split[5]).toString();
    }
}
