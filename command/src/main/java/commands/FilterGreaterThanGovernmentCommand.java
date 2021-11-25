package commands;

import core.CollectionManager;
import datee.Government;

public class FilterGreaterThanGovernmentCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public FilterGreaterThanGovernmentCommand(CollectionManager cm){
        this.collectionManager = cm;
    }

    @Override
    public boolean execute(String argument, Object oArgument) {

            Government government = Government.valueOf(argument);
            collectionManager.printElementsByNotEquals(government);



        return true;
    }
}
