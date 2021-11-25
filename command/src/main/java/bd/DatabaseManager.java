package bd;

import java.sql.*;

public class DatabaseManager {

    private final String DRIVER = "org.postgresql.Driver";

    private final String url;
    private final String globalUser;
    public Connection connection;

    public DatabaseManager(String url, String globalUser,String password){
        this.url = url;
        this.globalUser = globalUser;
        try {
            establishConnectionWithDatabase(password);
        }catch (SQLException e){
            System.out.println("Возникла ошибка соединения с базой данных. Будет осуществлен аварийный выход из сервера");
            System.exit(1);
        }
    }

    public void establishConnectionWithDatabase(String password) throws SQLException{

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, globalUser, password);
            System.out.println("Соединение с базой данных установлено");
        }catch (ClassNotFoundException | SQLException exception){
            throw new SQLException();
        }
    }

    public PreparedStatement getPreparedStatement(String pattern, boolean generatedKeyExpected){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(pattern, generatedKeyExpected ? Statement.RETURN_GENERATED_KEYS: Statement.NO_GENERATED_KEYS);
            return statement;
        }catch (SQLException e){
            System.out.println("Оштбка при обработке SQL запроса.");
        }
        return null;
    }

    public void closeStatement(Statement statement){
        try {
            statement.close();
        }catch (SQLException e){
            System.out.println("Ошибка при попытке закрыть объект запроса!");
        }
    }

    public void setRegulatedCommit(){
        try {
            connection.setAutoCommit(false);
        }catch (SQLException e){
            System.out.println("Ошибка при изменении режима работы с базой данных");
        }
    }

    public Savepoint setSavepoint(){
        try {
            return connection.setSavepoint();
        }catch (SQLException e){
            System.out.println("Ошибка при установке чекпоинта в режиме ручных коммитов");
        }
        return null;
    }

    public void rollback(Savepoint savepoint){
        try {
            if(!connection.getAutoCommit()){
                connection.rollback(savepoint);
            }
        }catch (SQLException e){
            System.out.println("Ошибка при отмене изменений");
        }
    }

    public void setAutoCommit(){
        try {
            connection.setAutoCommit(true);
        }catch (SQLException e){
            System.out.println("Ошибка при установке AutoCommit режима базы данных");
        }
    }

    public void commit(){
        try {
            connection.commit();
        }catch (SQLException e){
            System.out.println("Ошибка при утверждении изменений");
        }
    }
}
