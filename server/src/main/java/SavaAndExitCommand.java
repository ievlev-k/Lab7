import core.CollectionManager;

import java.util.Scanner;

public class SavaAndExitCommand implements Runnable {
    private final String outFile;
    private final CollectionManager collectionManager;
    public SavaAndExitCommand(String outFile, CollectionManager collectionManager){
        this.outFile = outFile;
        this.collectionManager = collectionManager;
    }
    @Override
    public void run() {

        Scanner SavaAndExitScanner = new Scanner(System.in);
        while (true){
            if (!SavaAndExitScanner.hasNextLine()) System.exit(-1);
            String[] userCommand = SavaAndExitScanner.nextLine().trim().split(" ");
            if (userCommand.length > 1) {
                System.out.println("Неверная комманда! Эти комманды не содержат аргументов!");
                continue;
            }
            if (userCommand[0].equals("exit")){
                System.out.println("Сервер отключился!");
                System.exit(0);
            }
            if (userCommand[0].equals("save")){

                collectionManager.save(outFile);
                System.out.println("Коллекция сохранена!");
            }
        }
    }
}
