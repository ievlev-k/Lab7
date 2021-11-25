package core;

import bd.DatabaseManager;
import bd.DbConstant;
import interaction.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler {
    private final DatabaseManager databaseManager;

    public UserHandler(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public User getUserByName(String name)throws SQLException{
        User user = null;
        PreparedStatement getUserStatement = null;
        try {
            getUserStatement = databaseManager.getPreparedStatement(DbConstant.SELECT_CITIES_BY_NAME, false);
            getUserStatement.setString(1, name);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()){
                user = new User(resultSet.getString(DbConstant.USER_NAME_COLUMN_IN_USERS), resultSet.getString(DbConstant.USER_NAME_COLUMN_IN_USERS));
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выборке пользователя по имени из базы данных");
            throw e;
        }finally {
            if (getUserStatement != null) databaseManager.closeStatement(getUserStatement);
        }
        return user;
    }

    public boolean findUserByNameAndPass(User user) throws SQLException{
        PreparedStatement getUserStatement = null;
        try {
            getUserStatement = databaseManager.getPreparedStatement(DbConstant.SELECT_USER_BY_NAME_AND_PASSWORD, false);
            getUserStatement.setString(1, user.getUsername());
            getUserStatement.setString(2, user.getPassword());
            ResultSet resultSet = getUserStatement.executeQuery();
            return resultSet.next();
        }finally {
            if (getUserStatement != null) databaseManager.closeStatement(getUserStatement);
        }
    }

    public boolean insertUser(User user) throws SQLException{
        if (getUserByName(user.getUsername())!= null) return false;
        PreparedStatement insertUserStatement = databaseManager.getPreparedStatement(DbConstant.INSERT_USER, false);
        insertUserStatement.setString(1, user.getUsername());
        insertUserStatement.setString(2, user.getPassword());
        if (insertUserStatement.executeUpdate() == 0) throw new SQLException();
        return true;
    }
}
