package commands;

import core.CollectionManager;
import core.ResponseOutput;
import interaction.User;

public class RemoveFirstCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public boolean execute(User user) {
        if (collectionManager.size() == 0) {
            ResponseOutput.appendln("Коллукция пуста");
        } else {
            if (collectionManager.removeFirst(user)) {
                collectionManager.getCollectionHandler().deleteFirst(user, collectionManager.listCity.peekFirst());
                collectionManager.listCity.remove(collectionManager.listCity.peekFirst());

                ResponseOutput.appendln("Первый элемент удален");
            }else {
                ResponseOutput.appendln("Первый элемент не принадлежит вам!");
            }
        }
        return true;
    }
}