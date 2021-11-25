package commands;

import core.CollectionManager;
import core.ResponseOutput;

public class InfoCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private static StringBuilder stringBuilder = new StringBuilder();

    public InfoCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public boolean execute() {
        ResponseOutput.appendln("Сведения о коллекции:");
        ResponseOutput.appendln("Тип коллекции: ArrayDequeue");
        ResponseOutput.appendln("Дата инициализации: " + collectionManager.getCreationDate());
        ResponseOutput.appendln("Размер коллекции: " + collectionManager.size());




        return true;
    }
}
