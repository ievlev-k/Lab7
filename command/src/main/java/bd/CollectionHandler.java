package bd;

import core.UserHandler;
import datee.*;
import interaction.User;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.sql.*;

public class  CollectionHandler {

    public DatabaseManager dbManager;
    public UserHandler userHandler;

    public CollectionHandler(DatabaseManager dbManager){
        this.dbManager = dbManager;
    }


    public ArrayDeque<City> loadCollectionFromDB() throws SQLException{
        ArrayDeque<City> collection = new ArrayDeque<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbManager.getPreparedStatement(DbConstant.SELECT_ALL_CITY, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                collection.add(getCityFromSet(resultSet));
            }
        }catch (SQLException e){
            System.out.println("Ошибка пр загрузке данных!");

        }finally {
            if (preparedStatement != null) dbManager.closeStatement(preparedStatement);
        }
        return collection;
    }

    private City getCityFromSet (ResultSet resultSet) throws SQLException{
        City newCity = new City();
        long id = resultSet.getInt(DbConstant.CITY_ID);
        newCity.setId(id);
        String name = resultSet.getString(DbConstant.CITY_NAME);
        newCity.setName(name);
        Coordinates coordinates = new Coordinates(resultSet.getLong(DbConstant.CITY_X), resultSet.getInt(DbConstant.CITY_Y));
        newCity.setCoordinates(coordinates);
        ZonedDateTime creationDate = ZonedDateTime.of(resultSet.getTimestamp(DbConstant.CREATION_DATE_COLUMN_IN_CITIES).toLocalDateTime(), ZoneId.of(resultSet.getString(DbConstant.CREATION_DATE_ZONE_COLUMN_IN_CITIES)));
        newCity.setCreationDate(creationDate);
        float area = resultSet.getFloat(DbConstant.CITY_AREA);
        newCity.setArea(area);
        long population = resultSet.getLong(DbConstant.CITY_POPULATION);
        newCity.setPopulation(population);
        int metersAboveSeaLevel = resultSet.getInt(DbConstant.CITY_METERS_ABOVE_SEA_LEVEL);
        newCity.setMetersAboveSeaLevel(metersAboveSeaLevel);
        boolean capital = resultSet.getBoolean(DbConstant.CITY_CAPITAL);
        newCity.setCapital(capital);
        Government government = Government.valueOf(resultSet.getString(DbConstant.CITY_GOVERNMENT));
        newCity.setGovernment(government);
        StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(resultSet.getString(DbConstant.CITY_STANDARD_OF_LIVING));
        newCity.setStandardOfLiving(standardOfLiving);
        Human governor = new Human(resultSet.getString(DbConstant.CITY_GOVERNOR));
        newCity.setHumen(governor);
        String user = resultSet.getString(DbConstant.USER_NAME);
        newCity.setUser(user);
        return newCity;

    }

    public City addNewCity(City city, User user) throws SQLException{

        PreparedStatement insertCityStatement;

        dbManager.setRegulatedCommit();

        Savepoint savepoint = dbManager.setSavepoint();

        insertCityStatement = dbManager.getPreparedStatement(DbConstant.INSERT_CITY, true);

        try {

            ZonedDateTime creation = ZonedDateTime.now();
            Coordinates coordinates = city.getCoordinates();

            insertCityStatement.setString(1, city.getName());
            insertCityStatement.setLong(2, coordinates.getX());
            insertCityStatement.setInt(3, coordinates.getY());

            insertCityStatement.setTimestamp(4, Timestamp.valueOf(creation.toLocalDateTime()));
            insertCityStatement.setString(5, creation.getZone().toString());
            insertCityStatement.setFloat(6, city.getArea());
            insertCityStatement.setLong(7, city.getPopulation());
            insertCityStatement.setInt(8, city.getMetersAboveSeaLevel());
            insertCityStatement.setBoolean(9, city.getCapital());

            insertCityStatement.setString(10, city.getGovernment().toString());
            insertCityStatement.setString(11, city.getStandardOfLiving().toString());

            insertCityStatement.setString(12, city.getGovernor().getName());



//            if(!userHandler.findUserByNameAndPass(user)) throw  new SQLException();

            insertCityStatement.setString(13, user.getUsername());

            if (insertCityStatement.executeUpdate() == 0) {

                throw new SQLException();
            }

            ResultSet preparedCityKeys = insertCityStatement.getGeneratedKeys();

            long cityId;

            if (preparedCityKeys.next()){
                cityId = preparedCityKeys.getInt(1);
            }else throw new SQLException();

            city.setCreationDate(creation);
            city.setId(cityId);

            dbManager.commit();
            city.setUser(user.getUsername());
            return city;

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выполнении запросов на добавление нового объекта в бд!");
            dbManager.rollback(savepoint);
            throw e;
        }finally {
            dbManager.closeStatement(insertCityStatement);
            dbManager.setAutoCommit();
        }
    }

    public void deleteById(long id, User user) throws SQLException{

        PreparedStatement deleteCitiesStatement;

        dbManager.setRegulatedCommit();

        Savepoint savepoint = dbManager.setSavepoint();

        deleteCitiesStatement = dbManager.getPreparedStatement(DbConstant.DELETE_BY_ID,false);

        try{
            deleteCitiesStatement.setLong(1, id);
            deleteCitiesStatement.setString(2, user.getUsername());
            deleteCitiesStatement.executeUpdate();
            dbManager.commit();
        }catch (SQLException e){
            System.out.println("Ошибка при выполнении запросов на удаление принадлежащих пользователю объектов из бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(deleteCitiesStatement);
            dbManager.setAutoCommit();
        }

    }
    public void deleteAllCitiesBelongToUser(User user) throws SQLException{


        PreparedStatement deleteCitiesStatement;

        dbManager.setRegulatedCommit();

        Savepoint savepoint = dbManager.setSavepoint();

        deleteCitiesStatement = dbManager.getPreparedStatement(DbConstant.DELETE_CITIES_BY_USER,false);

        try {
            deleteCitiesStatement.setString(1, user.getUsername());

            deleteCitiesStatement.executeUpdate();
            dbManager.commit();
        }catch (SQLException e){
            System.out.println("Ошибка при выполнении запросов на удаление принадлежащих пользователю объектов из бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(deleteCitiesStatement);
            dbManager.setAutoCommit();
        }


    }

    public void deleteFirst(User user, City city) {
        PreparedStatement deleteFirst;
        dbManager.setRegulatedCommit();
        Savepoint savepoint = dbManager.setSavepoint();
        deleteFirst = dbManager.getPreparedStatement(DbConstant.DELETE_BY_ID,false);
        try {
            deleteFirst.setLong(1, city.getId());
            deleteFirst.setString(2, user.getUsername());
            deleteFirst.executeUpdate();
            dbManager.commit();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выполнении запросов на удаление принадлежащих пользователю объектов из бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(deleteFirst);
            dbManager.setAutoCommit();
        }
    }

    public void deleteLower(User user, City city) {
        PreparedStatement deleteLower;
        dbManager.setRegulatedCommit();
        Savepoint savepoint = dbManager.setSavepoint();
        deleteLower = dbManager.getPreparedStatement(DbConstant.DELETE_LOWER,false);
        try {
            deleteLower.setInt(1, city.getMetersAboveSeaLevel());
            deleteLower.setString(2, user.getUsername());
            deleteLower.executeUpdate();
            dbManager.commit();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выполнении запросов на удаление принадлежащих пользователю объектов из бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(deleteLower);
            dbManager.setAutoCommit();
        }
    }

    public void deleteGreater(User user, City city) {
        PreparedStatement deleteGreater;
        dbManager.setRegulatedCommit();
        Savepoint savepoint = dbManager.setSavepoint();
        deleteGreater = dbManager.getPreparedStatement(DbConstant.DELETE_GREATER,false);
        try {
            deleteGreater.setInt(1, city.getMetersAboveSeaLevel());
            deleteGreater.setString(2, user.getUsername());
            deleteGreater.executeUpdate();
            dbManager.commit();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выполнении запросов на удаление принадлежащих пользователю объектов из бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(deleteGreater);
            dbManager.setAutoCommit();
        }
    }

    public void updateCityById(long id, City city, User user) throws SQLException{
        PreparedStatement updateCity = dbManager.getPreparedStatement(DbConstant.UPDATE_CITY_BY_ID, false);
        dbManager.setRegulatedCommit();
        Savepoint savepoint = dbManager.setSavepoint();
        try {

                updateCity.setString(1, city.getName());
                updateCity.setLong(2, city.getCoordinates().getX());
                updateCity.setInt(3, city.getCoordinates().getY());
                updateCity.setFloat(4, city.getArea());
                updateCity.setLong(5, city.getPopulation());
                updateCity.setInt(6, city.getMetersAboveSeaLevel());
                updateCity.setBoolean(7, city.getCapital());
                updateCity.setString(8, city.getGovernment().toString());
                updateCity.setString(9, city.getStandardOfLiving().toString());
                updateCity.setString(10, city.getGovernor().getName());

                updateCity.setLong(11, id);
                updateCity.setString(12, user.getUsername());

                if (updateCity.executeUpdate() == 0) throw new SQLException();


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ошибка при выполнении запросов на изменение города по id в бд!");
            dbManager.rollback(savepoint);

        }finally {
            dbManager.closeStatement(updateCity);
            dbManager.setAutoCommit();
        }


    }
}
