package commands;

import core.CollectionManager;
import core.CommandAsker;
import core.InputChecker;
import core.ResponseOutput;
import interaction.User;

import java.sql.SQLException;

public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final InputChecker inputChecker;
    private final CommandAsker commandAsker;


    public RemoveByIdCommand(CollectionManager cm, InputChecker ic, CommandAsker ca) {
        this.collectionManager = cm;
        this.inputChecker = ic;
        this.commandAsker = ca;

    }

    @Override
    public boolean execute(String sArgument, Object oArgument, User user) {

            long id = Long.parseLong(sArgument);
            if (!collectionManager.removeByID(id, user)) {
                ResponseOutput.appendln("Такого Id нет");
                return true;
            }
            try {
                collectionManager.getCollectionHandler().deleteById(id, user);
            }catch (SQLException e){
                System.out.println("\"Произошла ошибка при удалении городов, принадлежащих пользователю, из базы данных");
            }
            ResponseOutput.appendln("City с Id = " + id + " успешно удален");
            return true;


    }


}
