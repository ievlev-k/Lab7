package util;

import interaction.Request;
import interaction.User;

import java.util.Scanner;

public class AuthHandler {
    private final String loginCommand = "login";
    private final String registerCommand = "register";

    private Scanner userScanner;

    public AuthHandler(Scanner userScanner){
        this.userScanner = userScanner;
    }


    public Request handle(){
        AuthAsker authAsker = new AuthAsker(userScanner);
        String command = authAsker.askQuestion("У вас уже есть учетная запись?")? loginCommand : registerCommand;
        User user = new User(authAsker.askLogin(), authAsker.askPassword());
        return new Request(command, null,null, user);
    }
}
