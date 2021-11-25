package commands;

import core.CollectionManager;
import core.ResponseOutput;
import interaction.User;

import java.sql.SQLException;

public class LoginCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public LoginCommand(CollectionManager collectionManager){
        this.collectionManager =collectionManager;
    }

    @Override
    public boolean execute(String argument, Object oArgument, User user) {
        try {
            if (collectionManager.getUserHandler().findUserByNameAndPass(user)){
                ResponseOutput.appendln("Пользователь был найден в базе данных, пароль верный");
                return true;
            }else {
                ResponseOutput.appendln("Неверное имя пойльзователя или пароль!");
                return false;
            }
        }catch (SQLException e){
            ResponseOutput.appendln("Произошла ошибка при обращении к базе данных");
        }
        return false;
    }
}
