package commands;

import core.CollectionManager;
import core.ResponseOutput;
import interaction.User;

import java.sql.SQLException;

public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public boolean execute(User user) {
        try {


            collectionManager.getCollectionHandler().deleteAllCitiesBelongToUser(user);
            collectionManager.deleteElementsByUser(user.getUsername());
            ResponseOutput.appendln("Были удвлены элементы, созданные вами!");
        }catch (SQLException e){
            System.out.println("\"Произошла ошибка при удалении городов, принадлежащих пользователю, из базы данных");
        }

        return true;
    }
}
