package bd;

import datee.City;

public class DbConstant {
    /*
    Constants
     */
    public static final String CITY_TABLE = "city";
    public static final String USER_TABLE = "users";

    public static final String CITY_ID = "cityId";
    public static final String CITY_NAME = "cityName";
    public static final String CITY_X = "cityX";
    public static final String CITY_Y = "cityY";
    public static final String CREATION_DATE_COLUMN_IN_CITIES = "creationDate";
    public static final String CREATION_DATE_ZONE_COLUMN_IN_CITIES = "creationDateZone";
    public static final String CITY_AREA = "cityArea";
    public static final String CITY_POPULATION = "cityPopulation";
    public static final String CITY_METERS_ABOVE_SEA_LEVEL = "cityMetersAboveSeaLevel";
    public static final String CITY_CAPITAL = "cityCapital";
    public static final String CITY_GOVERNMENT = "cityGovernment";
    public static final String CITY_STANDARD_OF_LIVING = "cityStandardOfLiving";
    public static final String CITY_GOVERNOR = "cityGovernor";
    public static final String USER_NAME = "userName";

    public static final String USER_NAME_COLUMN_IN_USERS = "userName";
    public static final String USER_PASSWORD_COLUMN_IN_USERS = "hashPass";
    /*

    SELECTS
     */
    public static final String SELECT_ALL_CITY = "SELECT * FROM " + CITY_TABLE;

    public static final String SELECT_CITIES_BY_NAME = "SELECT * FROM  " + USER_TABLE + " WHERE " + USER_NAME_COLUMN_IN_USERS + " = ?";

    public static final String SELECT_USER_BY_NAME_AND_PASSWORD = "SELECT * FROM " + USER_TABLE+ " WHERE " + USER_NAME_COLUMN_IN_USERS +
            " = ? AND " + USER_PASSWORD_COLUMN_IN_USERS + " = ?";
    /*
    INSERT
     */

    public static final String INSERT_CITY = "INSERT INTO " + CITY_TABLE
            + " ("
            + CITY_NAME + ", "
            + CITY_X + ", "
            +   CITY_Y + ", "
            + CREATION_DATE_COLUMN_IN_CITIES + ", "
            + CREATION_DATE_ZONE_COLUMN_IN_CITIES + ", "
            + CITY_AREA + ", "
            + CITY_POPULATION + ", "
            + CITY_METERS_ABOVE_SEA_LEVEL + ", "
            + CITY_CAPITAL + ", "
            + CITY_GOVERNMENT + ", "
            + CITY_STANDARD_OF_LIVING + ", "
            + CITY_GOVERNOR + ", "
            + USER_NAME + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERT_USER = "INSERT INTO " + USER_TABLE + " VALUES (?, ?)";
    /*
    DELETES
    */
    public static  final String DELETE_CITIES_BY_USER  = "DELETE FROM " + CITY_TABLE+ " WHERE " + USER_NAME + " = ?";
    public static final String DELETE_BY_ID = "DELETE FROM " + CITY_TABLE + " WHERE " + CITY_ID + " = ? AND " + USER_NAME + " = ?";
    public static final String DELETE_LOWER = "DELETE FROM " + CITY_TABLE + " WHERE " + CITY_METERS_ABOVE_SEA_LEVEL + " < ? AND " + USER_NAME + " = ?";
    public static final String DELETE_GREATER = "DELETE FROM " + CITY_TABLE + " WHERE " + CITY_METERS_ABOVE_SEA_LEVEL + " > ? AND " + USER_NAME + " = ?";
    public static final String DELETE_FIRST = "DELETE FROM " + CITY_TABLE + " WHERE " + USER_NAME + " = ? " + " LIMIT 1 ORDER BY " + CITY_TABLE;
    /*
    UPDATES
     */
    public static final String UPDATE_CITY_BY_ID = "UPDATE " + CITY_TABLE + " SET "
            + CITY_NAME + " = ?, "
            + CITY_X + " = ?, "
            + CITY_Y + " = ?, "
            + CITY_AREA+ " = ?, "
            + CITY_POPULATION + " = ?, "
            + CITY_METERS_ABOVE_SEA_LEVEL + " = ?, "
            + CITY_CAPITAL+ " = ?, "
            + CITY_GOVERNMENT + " = ?, "
            + CITY_STANDARD_OF_LIVING + " = ?, "
            + CITY_GOVERNOR + " = ? "
            + " WHERE " + CITY_ID + " = ? AND "
            + USER_NAME + " = ?";


}
