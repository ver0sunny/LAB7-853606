package Client;

import Client.commands.AddCommand;
import Common.DataObjectToSend;
import Common.InputAndVerifier;
import Common.Parameters;
import Common.Serializer;

import java.util.Scanner;

public class ClientRunner {
    public static void main(String[] args) {

        Serializer serializer = new Serializer();
        ConnectionBlock connectionChannel = new ConnectionBlock();
        ClientManager clientManager = new ClientManager(serializer, connectionChannel);

        Scanner userInput = new Scanner(System.in);
        InputAndVerifier inputAndVerifier = new InputAndVerifier(userInput);
        CommandFactory commandFactory = new CommandFactory(new AddCommand(inputAndVerifier));
        CommandManager commandManager = new CommandManager(commandFactory);

        ClientConsole clientConsole = new ClientConsole(userInput,commandManager,clientManager,new DataObjectToSend());
//        clientConsole.serverMode();
        clientConsole.databaseConnection();
        clientConsole.serverMode();
    }
}
