package commands;

import core.CollectionManager;
import core.ResponseOutput;
import datee.StandardOfLiving;

public class FilterByStandardOfLivingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    public FilterByStandardOfLivingCommand(CollectionManager cm){
        this.collectionManager = cm;
    }

    @Override
    public boolean execute(String argument, Object oArgument) {
        ResponseOutput.appendln("Элементы с standardOfLiving = " + argument);


            StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(argument);
            if(standardOfLiving == StandardOfLiving.LOW){
                collectionManager.printElementsByEquals(standardOfLiving);
                return true;
            }
            if (standardOfLiving == StandardOfLiving.MEDIUM){
                collectionManager.printElementsByEquals(standardOfLiving);
                return true;
            }
            if (standardOfLiving == StandardOfLiving.ULTRA_LOW){
                collectionManager.printElementsByEquals(standardOfLiving);
                return true;
            }
            if (standardOfLiving == StandardOfLiving.VERY_HIGH){
                collectionManager.printElementsByEquals(standardOfLiving);
                return true;
            }
            if (standardOfLiving == StandardOfLiving.VERY_LOW){
                collectionManager.printElementsByEquals(standardOfLiving);
                return true;
            }
            ResponseOutput.appendln("Их нет!");
         return true;
    }
}
