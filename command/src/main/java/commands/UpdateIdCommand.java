package commands;

import core.CollectionManager;
import core.CommandAsker;
import core.InputChecker;
import core.ResponseOutput;
import datee.City;
import datee.CityForwarding;
import interaction.User;

import java.sql.SQLException;
import java.time.ZonedDateTime;

public class UpdateIdCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final InputChecker inputChecker;
    private final CommandAsker commandAsker;

    public UpdateIdCommand(CollectionManager cm, InputChecker ic, CommandAsker ca) {
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

            CityForwarding cityForwarding = (CityForwarding) oArgument;
            City newCity = new City();
            newCity.setId(id);
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
            newCity.setUser(user.getUsername());
            try{
                collectionManager.getCollectionHandler().updateCityById(id, newCity, user);
            }catch (SQLException e){
                ResponseOutput.appendln("Ошибка при обращении к базе данных!");
            }
            collectionManager.add(newCity);
            ResponseOutput.appendln("Сity успешно обновлен!");
            return true;




    }
}
