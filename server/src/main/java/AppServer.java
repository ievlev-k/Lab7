import bd.CollectionHandler;
import commands.*;
import core.*;
import bd.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AppServer {
    public static  int port;
    public static final int CONNECTION_TIMEOUT = 120000000;
    public static String fileName;
    public static SavaAndExitCommand savaAndExitCommand;
    public static void main(String[] args) {
        fileName = System.getenv("filename");

        if (fileName == null) {
            System.out.println("Не задано имя файла");
            System.exit(1);

        }
        File file = new File(fileName);

        try {


            if (!(!file.isDirectory() && file.isFile() && Files.isReadable(file.toPath().toRealPath()))) {
                System.out.println("Файл неправильный");
                System.exit(1);

            }
            if (!(file.canWrite()&&file.canRead())){
                System.out.println("Не могу читать данный файл!");
                System.exit(1);

            }        }catch (IOException e){
            System.out.println("Ошбка");
        }
        DatabaseManager dbManager = null;
        try {


            dbManager = new DatabaseManager("jdbc:postgresql://localhost:28282/studs", args[0], args[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите в в аргументе строки: username, password!");
            System.exit(-1);
        }
        CollectionHandler collectionHandler = new CollectionHandler(dbManager);
        UserHandler userHandler = new UserHandler(dbManager);
        CollectionManager collectionManager = new CollectionManager(collectionHandler,userHandler);
        collectionManager.loadCollectionFromDatabase();
        InputChecker inputChecker = new InputChecker();
        CommandAsker commandAsker = new CommandAsker(inputChecker);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new SaveCommand(collectionManager),
                new RemoveFirstCommand(collectionManager),
                new RemoveLowerCommand(collectionManager, commandAsker),
                new RemoveGreaterCommand(collectionManager,commandAsker),
                new FilterByStandardOfLivingCommand(collectionManager),
                new AddCommand(collectionManager,commandAsker),
                new InfoCommand(collectionManager),
                new FilterGreaterThanGovernmentCommand(collectionManager),
                new ShowCommand(collectionManager),
                new UpdateIdCommand(collectionManager,inputChecker,commandAsker),
                new RemoveByIdCommand(collectionManager,inputChecker, commandAsker),
                new ClearCommand(collectionManager),
                new ExitCommand(),
                new PrintAscendingCommand(collectionManager),
                new ExecuteScriptCommand(),
                new RegisterCommand(collectionManager),
                new LoginCommand(collectionManager)
        );

        port = (int) (Math.random()*65535);
        port = 28283;
        System.out.println("Сервер открылся на port = " + port);

        Server server = new Server(port, commandManager);
        savaAndExitCommand = new SavaAndExitCommand(fileName, collectionManager);
        Thread newThread = new Thread(savaAndExitCommand);
        newThread.start();
        server.start();
    }



}
