package Client;

import Common.DataObjectToSend;
import Common.Parameters;
import Common.exceptions.NoSuchCommandException;

import java.util.Scanner;

public class ClientConsole {

    private Scanner userInput;
    private CommandManager commandManager;
    private ClientManager clientManager;
    private DataObjectToSend dataObjectToSend;

    public ClientConsole(Scanner userInput, CommandManager commandManager, ClientManager clientManager, DataObjectToSend dataObjectToSend) {
        this.userInput = userInput;
        this.commandManager = commandManager;
        this.clientManager = clientManager;
        this.dataObjectToSend = dataObjectToSend;
    }

    public void databaseConnection() {
        String answer;
        String username;
        String password;
        boolean userSignedUp = false;
        int userNumber = 0;

        dataObjectToSend = clientManager.receive();
        String[] users = dataObjectToSend.getUserName().split(" ");
        String[] passes = dataObjectToSend.getUserPass().split(" ");

        DataObjectToSend dataObjectToReply = new DataObjectToSend();

        ClientConsole.println("––__––Do you have an account?––__––");
        answer = userInput.nextLine().trim();
        switch (answer.toUpperCase()) {
            case ("YES"):
                ClientConsole.println("––__––please enter login––__––");
                username = userInput.nextLine().trim();
                for (int i = 0; i < users.length; i++) {
                    if (username.equals(users[i])) {
                        userSignedUp = true;
                        dataObjectToReply.setUserName(username);
                        userNumber = i;
                        break;
                    }
                }
                if (userSignedUp) {
                    ClientConsole.println("––__––please enter password––__––");
                    password = userInput.nextLine().trim();
                    if (password.equals(passes[userNumber])) {
                        ClientConsole.println("––__––you've logged in successfully––__––");
                        dataObjectToReply.setUserPass(password);
                        break;

                    } else {
                        ClientConsole.println("––__––wrong password - try again––__––");
                        password = userInput.nextLine().trim();
                        if (password.equals(passes[userNumber])) {
                            ClientConsole.println("––__––you've logged in successfully––__––");
                            dataObjectToReply.setUserPass(password);
                            break;
                        }
                    }
                } else {
                    ClientConsole.println("––__––you don't have an account––__––");
                    ClientConsole.println("––__––would you like to create an account?––__––");
                    answer = userInput.nextLine().trim();
                    switch (answer.toUpperCase()) {
                        case ("YES"):
                            ClientConsole.println("––__––create a new username––__––");
                            username = userInput.nextLine().trim();
                            for (int j = 0; j < users.length; j++) {
                                if (username.equals(users[j])) {
                                    ClientConsole.println("––__––this username is taken, try another one––__––");
                                    username = userInput.nextLine().trim();
                                } else
                                    break;
                            }
                            dataObjectToReply.setUserName(username);
                            ClientConsole.println("––__––create password––__––");
                            password = userInput.nextLine().trim();
                            dataObjectToReply.setUserPass(password);
                            ClientConsole.println("––__––you've signed up successfully––__––");
                            break;

                        case ("NO"):
                            System.exit(0);
                    }
                }
                break;

            case ("NO"):
                ClientConsole.println("––__––would you like to create an account?––__––");
                answer = userInput.nextLine().trim();
                switch (answer.toUpperCase()) {
                    case ("YES"):
                        ClientConsole.println("––__––create a new username––__––");
                        username = userInput.nextLine().trim();
                        for (int j = 0; j < users.length; j++) {
                            if (username.equals(users[j])) {
                                ClientConsole.println("––__––this username is taken, try another one––__––");
                                username = userInput.nextLine().trim();
                            } else
                                break;
                        }
                        dataObjectToReply.setUserName(username);
                        ClientConsole.println("––__––create password––__––");
                        password = userInput.nextLine().trim();
                        dataObjectToReply.setUserPass(password);
                        ClientConsole.println("––__––you've signed up successfully––__––");
                        break;

                    case ("NO"):
                        System.exit(0);
                }
        }
        clientManager.send(dataObjectToReply);
    }

    public void serverMode() {
        String[] command;
        try {
            do {
                command = (userInput.nextLine().trim() + " ").split(" ", 2);
                command[1] = command[1].trim();
                String commandName = command[0].toUpperCase();
                String commandArguments = command[1];
                dataObjectToSend.setName("COMMAND");
                switch (commandName) {
                    case ("ADD"):
                        String commandOutput = commandManager.execute(commandName, commandArguments);
                        dataObjectToSend.setCommandToSend(commandName);
                        dataObjectToSend.setArgs(commandOutput);
                        break;
                    case ("HELP"):
                    case ("SHOW"):
                    case ("HISTORY"):
                    case ("INFO"):
                    case ("SAVE"):
                    case ("CLEAR"):
                    case ("PRINT_FIELD_DESCENDING_SEMESTER_ENUM"):
                        dataObjectToSend.setCommandToSend(commandName);
                        dataObjectToSend.setArgs(commandArguments);
                        break;
                    default:
                        dataObjectToSend.setName("MISTAKE");
                        ClientConsole.printerror("No such command exist");
                }
                clientManager.send(dataObjectToSend);
                DataObjectToSend dataObjectToSend = clientManager.receive();
                switch (dataObjectToSend.getName()) {
                    case ("EXECUTED_COMMAND"):
                        ClientConsole.println(dataObjectToSend.getMessage());
                        break;
                    case ("FAIL"):
                        dataObjectToSend.getMessage();
                    case ("AWFUL_FAIL"):
                        ClientConsole.println("Something somewhere went wrong");
                }
            } while (true);
        } catch (NoSuchCommandException e) {
            ClientConsole.printerror("No such command exist, check the list of available commands by calling 'help' command");
        }
    }


    public static void print(Object someThing) {
        System.out.print(someThing);
    }

    public static void println(Object someThing) {
        System.out.println(someThing);
    }

    public static void printerror(Object someThing) {
        System.out.println("ERROR~ERROR ~ " + someThing);
    }

    @Override
    public String toString() {
        return "Console - works with output and input";
    }

}

