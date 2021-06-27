package Common;

import java.io.Serializable;

public class UserInfo implements Serializable {
    String username;
    String password;
    private static final long serialVersionUID = 49L;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String hashPassword(String password) {
        String hashed = Md5Coder.md5Custom(password);
        return hashed;
    }

}
