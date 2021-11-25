package util;

import java.util.Scanner;

public class AuthAsker {
    private final Scanner userScanner;

    public AuthAsker(Scanner userScanner){
        this.userScanner = userScanner;
    }

    public String askLogin(){
        String login;
        while (true){
            System.out.println("Введите логин:");
            if(!userScanner.hasNextLine())System.exit(-1);
            login = userScanner.nextLine().trim();if (login.equals("")){
                System.out.println("Такого логина не существует!");
                continue;
            }
            break;
        }
        return login;
    }

    public String askPassword(){
        String password;
        while (true){
            System.out.println("Введите пароль:");
            if(!userScanner.hasNextLine())System.exit(-1);
            password = userScanner.nextLine().trim();if (password.equals("")){
                System.out.println("Такого пароля не существует!");
                continue;
            }
            break;
        }
        return password;
    }

    public boolean askQuestion(String question){
        System.out.println("Вы не можете пользоваться командами, пока вы не вошли в учетную запись.");
        String finalQuestion = question + "(yes/no):";
        String answer;
        while (true){
            System.out.println(finalQuestion);
            if (!userScanner.hasNextLine())System.exit(-1);
            answer = userScanner.nextLine().trim();
            if (!(answer.equals("yes") || answer.equals("no"))){
                System.out.println("Такого ответа нет!");
                continue;
            }
            break;
        }
        return answer.equals("yes");
    }


}
