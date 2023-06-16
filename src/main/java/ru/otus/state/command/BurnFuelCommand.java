package ru.otus.state.command;

import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;
import ru.otus.state.utils.DoubleValueChecker;

import static java.util.Objects.nonNull;

/**
 * BurnFuelCommand уменьшает количество топлива на скорость расхода топлива.
 */
public class BurnFuelCommand implements DoubleValueChecker, Command {

    private final GameSetting gameSetting;

    public BurnFuelCommand(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    @Override
    public boolean execute() {
        burnFuel();

        return true;
    }

    private void burnFuel() {

        if (nonNull(gameSetting)) {

            double fuel = gameSetting.getFuel();

            double consumption = gameSetting.getConsumption();

            valueChecker(fuel);

            valueChecker(consumption);

            double valueOfFuelAfterBurn = fuel - consumption;

            gameSetting.setFuel(valueOfFuelAfterBurn);

        } else {
            throw new CommandException("Настройки игры не установлены");
        }
    }

}
