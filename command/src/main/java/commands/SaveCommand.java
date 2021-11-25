package commands;

import core.CollectionManager;

public class SaveCommand  extends AbstractCommand{
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public boolean execute(String fileName) {
        collectionManager.save(fileName);

        return true;
    }
}
