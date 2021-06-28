package Server;

import Common.DataObjectToSend;
import Common.exceptions.NoSuchCommandException;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class ServerConsole {
    private CommandManager commandManager;
    private ServerManager serverManager;
    private DataObjectToSend dataObjectToSend;
    private String userName;
    private String userPass;

    public ServerConsole(CommandManager commandManager, ServerManager serverManager, DataObjectToSend dataObjectToSend) {
        this.commandManager = commandManager;
        this.serverManager = serverManager;
        this.dataObjectToSend = dataObjectToSend;
    }

    public String getUserName() { return userName;}

    public void databaseConnecction() {
        Connection c = null;
        Statement stmt = null;
        String users = new String();
        String passwords = new String();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                    "postgres", "12345678");
            ServerConsole.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS;" );
            while ( rs.next() ) {
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                users = users + " " + userName;
                passwords = passwords + " " + userPassword;
            }
            rs.close();
            stmt.close();
            c.close();

            dataObjectToSend.setUserName(users);
            dataObjectToSend.setUserPass(passwords);
            serverManager.send(dataObjectToSend);

            dataObjectToSend = serverManager.receive();
            userName = dataObjectToSend.getUserName();
            System.setProperty("username", userName);
            userPass = dataObjectToSend.getUserPass();

            if (dataObjectToSend.getName().equals("NEW_USER")) {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studyGroupCollection",
                        "postgres", "12345678");
                stmt = c.createStatement();
                String sql = "INSERT INTO USERS (USER_NAME,USER_PASSWORD) "
                        + "VALUES ('" + dataObjectToSend.getUserName() + "', '" + dataObjectToSend.getUserPass() + "')";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        public void serverMode() {
        try {
            do {
                DataObjectToSend dataObjectToSend = serverManager.receive();
                switch (dataObjectToSend.getName()) {
                    case ("COMMAND"):
                        String commandName = dataObjectToSend.getCommandToSend().getName();
                        String commandArgs = dataObjectToSend.getArgs();
                        String commandOutput = new String();
                        switch (commandName) {
                            case ("ADD"):
                            case ("HELP"):
                            case ("HISTORY"):
                            case ("SAVE"):
                            case ("SHOW"):
                            case ("INFO"):
                            case ("CLEAR"):
                            case ("PRINT_FIELD_DESCENDING_SEMESTER_ENUM"):
                                commandOutput = commandManager.execute(commandName,commandArgs);
                                dataObjectToSend.setName("EXECUTED_COMMAND");
                                dataObjectToSend.setMessage(commandOutput);
                                break;

//                            case ("FILTER_GREATER_THAN_FORM_OF_EDUCATION"):
//                                commandOutput = commandManager.execute(commandName,commandArgs);
//                                if (commandOutput.equals("Collection is empty T-T")) dataObjectToSend.setName("FAIL");
//                                dataObjectToSend.setMessage(commandOutput);
//                                break;
                        }
                        break;
                    case ("MISTAKE"):
                        ServerConsole.printerror("Client mistake");
                }
                serverManager.send(dataObjectToSend);
            } while (true);
        } catch (NoSuchElementException exception) {
            ServerConsole.printerror("No user input detected");
        } catch (IllegalStateException exception) {
            ServerConsole.printerror("Something unexpected went wrong");
        } catch (NoSuchCommandException e) {
            ServerConsole.printerror("No such command found");
        }
    }

    public static void print(Object someThing) {
        System.out.print("Server Message ~ " + someThing);
    }
    public static void println(Object someThing) {
        System.out.println("Server Message ~ " + someThing);
    }
    public static void printerror(Object someThing) {
        System.out.println("Server Message ~ " + "ERROR~ERROR ~ " + someThing);
    }

    @Override
    public String toString() {
        return "Console - works with output and input";
    }

}
