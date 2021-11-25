package commands;

import core.CollectionManager;

public class PrintAscendingCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public PrintAscendingCommand(CollectionManager cm){
        this.collectionManager = cm;
    }

    @Override
    public boolean execute() {
        if (collectionManager.size() == 0){
            System.out.println("Коллекция пуста!");
        }else{collectionManager.printAscending();}
        return true;
    }
}
