package core;

import datee.*;
import datee.CityForwarding;
import interaction.Request;
import interaction.ResponseCode;
import interaction.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import java.util.Scanner;
import java.util.Stack;

public class ClientHandler {
    private final HashMap<String, Boolean> inStack = new HashMap<>();
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();
    private InputChecker inputChecker = new InputChecker();
    private CommandAsker commandAsker = new CommandAsker(inputChecker);
    Scanner userScanner = new Scanner(System.in);;





    public Request handle(ResponseCode serverResponseCode, User user) throws IOException{
            String userInput;
            String[] userCommand;
            ProcessingCode processingCode;
            do {
                if (!scannerStack.isEmpty() && (serverResponseCode == ResponseCode.ERROR || serverResponseCode == ResponseCode.SERVER_EXIT)){
                    while (!scannerStack.isEmpty()) {
                    userScanner.close();
                    userScanner = scannerStack.pop();
                }
                    scriptStack.clear();
                    return new Request(user);}
                while (!scannerStack.isEmpty() && !userScanner.hasNextLine()) {
                    userScanner.close();
                    userScanner = scannerStack.pop();

                }
                if (!userScanner.hasNextLine()){
                    System.exit(-1);
                }
                userInput = userScanner.nextLine();
                userCommand = (userInput.trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                processingCode = conditionCode(userCommand[0], userCommand[1]);

            }while (processingCode == ProcessingCode.ERROR && scannerStack.isEmpty() || userCommand[0].isEmpty());
            try {
                switch (processingCode) {
                    case OBJECT:
                        CityForwarding cityForwarding = new CityForwarding(commandAsker.nameAsker(), commandAsker.coordinatesAsker(), commandAsker.areaAsker(), commandAsker.populationAsker(), commandAsker.metersAboveSeaLevelAsker(), commandAsker.capitalAsker(), commandAsker.askerGoverment(), commandAsker.standardOfLivingAsker(), commandAsker.humanAsker());
                        return new Request(userCommand[0], userCommand[1], cityForwarding, user);
                    case SCRIPT:

                        File scriptFile = new File(userCommand[1]);

                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        System.out.println("Выполняю скрипт " + scriptFile.getName());
                        break;
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Файл не наден");

            }
            return new Request(userCommand[0], userCommand[1], user);

    }
    private ProcessingCode conditionCode(String command, String commandArgument){

            switch (command) {
                case "help":

                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;

                case "info":
                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) {
                        System.out.println("У этой команды нет аргумента");
                        return ProcessingCode.ERROR;
                    }
                    return ProcessingCode.OBJECT;
                case "clear":
                    if (!commandArgument.isEmpty()) {
                        System.out.println("У этой команды нет аргумента");
                        return ProcessingCode.ERROR;
                    }
                    break;

                case "execute_script":

                    if (commandArgument.isEmpty()) {
                        System.out.println("Нет аргумента");
                        return ProcessingCode.ERROR;
                    }

                    File file = new File(commandArgument);
                    try {
                        if (!(!file.isDirectory() && file.isFile() && Files.isReadable(file.toPath().toRealPath()))) {
                            System.out.println("Файл неправильный");
                            return ProcessingCode.ERROR;

                            }
                        if (!(file.canRead() &&file.canWrite()) ){
                            System.out.println("Не могу читать данный файл!");
                            return ProcessingCode.ERROR;
                        }}catch (IOException e){
                            System.out.println("Ошбка");
                            return ProcessingCode.ERROR;
                        }

                    if (inStack.get(commandArgument) != null) {
                        if (inStack.get(commandArgument)) {
                            System.out.println("Чтобы избежать рекурсии, файл " + commandArgument + " не может быть выполнен ");
                            return  ProcessingCode.ERROR;
                        }
                    }
                    inStack.put(commandArgument, true);
                    return ProcessingCode.SCRIPT;


                case "update":
                    if (commandArgument.isEmpty()) {
                        System.out.println("Вы не ввели агрумент");
                        return ProcessingCode.ERROR;
                    }
                    if (inputChecker.longChecker(commandArgument, 0, Long.MAX_VALUE, false)) {

                    return ProcessingCode.OBJECT;}
                    System.out.println("Введен неверный id. Тип id - Long, id больше 0");
                    return ProcessingCode.ERROR;

                case "remove_by_id":
                    if (commandArgument.isEmpty()) {
                        System.out.println("Вы не ввели агрумент");
                        return ProcessingCode.ERROR;
                    }
                    if (!inputChecker.longChecker(commandArgument, 0, Long.MAX_VALUE, false)) {
                        System.out.println("Введен неверный id. Тип id - Long, id больше 0");
                        return ProcessingCode.ERROR;}
                    break;

                case "exit":
                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;
                case "remove_first":
                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;
                case "remove_greater":
                    if (!commandArgument.isEmpty()) {
                        System.out.println("У этой команды нет аргумента");
                        return ProcessingCode.ERROR;
                    }
                    return ProcessingCode.OBJECT;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) {
                        System.out.println("У этой команды нет аргумента");
                        return ProcessingCode.ERROR;
                    }
                    return ProcessingCode.OBJECT;
                case "filter_by_standard_of_living":
                    if (commandArgument.isEmpty()) {
                        System.out.println("Вы не ввели агрумент");
                        return ProcessingCode.ERROR;
                    }
                    try {
                        StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(commandArgument);
                    }catch (IllegalArgumentException e){
                        System.out.println("Неверный standardOfLiving! Такого standardOfLiving нет");
                        System.out.println("Введите характер из следующего списка");
                        for (StandardOfLiving standard : StandardOfLiving.values()) {
                            System.out.println(standard);
                        }
                        return ProcessingCode.ERROR;}

                    break;
                case "filter_greater_than_government":
                    if (commandArgument.isEmpty()) {
                        System.out.println("Вы не ввели агрумент");
                        return ProcessingCode.ERROR;
                    }
                    try {
                        Government government = Government.valueOf(commandArgument);
                    }catch (IllegalArgumentException e){
                        System.out.println("Неверный Government! Такого Government нет");
                        System.out.println("Введите характер из следующего списка");
                        for (Government g : Government.values()) {
                            System.out.println(g);
                        }
                        return ProcessingCode.ERROR;
                    }
                    break;

                case "print_ascending":
                    if (!commandArgument.isEmpty()) {
                        return ProcessingCode.ERROR;
                    }
                    break;


                default:
                    System.out.println("Нет введненной команды");
                    return ProcessingCode.ERROR;
            }
        return ProcessingCode.OK;
    }






}
