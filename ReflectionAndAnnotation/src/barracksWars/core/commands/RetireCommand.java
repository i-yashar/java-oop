package barracksWars.core.commands;

import barracksWars.interfaces.Inject;
import barracksWars.interfaces.Repository;
import barracksWars.interfaces.UnitFactory;

public class RetireCommand extends Command{
    @Inject
    private String[] data;

    @Inject
    private Repository repository;

    public RetireCommand() {
    }

    @Override
    public String execute() {
        String unitType = this.data[1];
        try{
            this.repository.removeUnit(unitType);
            return unitType + " retired!";
        } catch (IllegalArgumentException ex){
            return ex.getMessage();
        }
    }
}
