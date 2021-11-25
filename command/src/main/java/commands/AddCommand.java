package commands;

import core.CollectionManager;
import core.CommandAsker;
import core.ResponseOutput;
import datee.City;
import datee.CityForwarding;
import interaction.User;

import java.sql.SQLException;
import java.time.ZonedDateTime;

public class AddCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final CommandAsker commandAsker;
    public AddCommand(CollectionManager cm, CommandAsker ca){
        this.collectionManager = cm;
        this.commandAsker = ca;

    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        CityForwarding cityForwarding = (CityForwarding) objectArgument;
        City newCity = new City();
        newCity.setId(collectionManager.idCreate());
        newCity.setName(cityForwarding.getName());
        newCity.setCoordinates(cityForwarding.getCoordinates());
        newCity.setCreationDate(ZonedDateTime.now());
        newCity.setArea(cityForwarding.getArea());
        newCity.setPopulation(cityForwarding.getPopulation());
        newCity.setMetersAboveSeaLevel(cityForwarding.getMetersAboveSeaLevel());
        newCity.setCapital(cityForwarding.getCapital());
        newCity.setGovernment(cityForwarding.getGovernment());
        newCity.setStandardOfLiving(cityForwarding.getStandardOfLiving());
        newCity.setHumen(cityForwarding.getGovernor());
        try {
            City city = collectionManager.getCollectionHandler().addNewCity(newCity, user);
            collectionManager.add(city);
            ResponseOutput.appendln("Город создан!");
        }catch (SQLException e){
            System.out.println("Возникла ошибка при добавлении города в базу данных");

        }




        return true;
    }
}
