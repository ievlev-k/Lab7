package commands;

import core.CollectionManager;
import core.ResponseOutput;
import interaction.User;

import java.sql.SQLException;

public class RegisterCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public RegisterCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String argument, Object oArgument, User user) {

        try {
            boolean successfullyInserted = collectionManager.getUserHandler().insertUser(user);
            if (!successfullyInserted){
                ResponseOutput.appendln("Пользователь с таким именем уже есть в базе!");
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
            ResponseOutput.appendln("Ошибка при обращении к базе данных, регистрация не осуществлена");
        }
        ResponseOutput.appendln("Пользователь успешно добавлен в базу данных!");

        return true;
    }
}
