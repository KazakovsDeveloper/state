package ru.otus.state.command;

import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;
import ru.otus.state.utils.DoubleValueChecker;

import static java.util.Objects.nonNull;

/**
 * CheckFuelCommand проверяет, что топлива достаточно, если нет, то выбрасывает исключение CommandException.
 */
public class CheckFuelCommand implements DoubleValueChecker, Command {

    private final GameSetting gameSetting;

    public CheckFuelCommand(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    @Override
    public boolean execute() {
        return checkFuel();
    }

    private boolean checkFuel() {
        if (nonNull(gameSetting)) {
            double fuel = gameSetting.getFuel();
            double consumption = gameSetting.getConsumption();

            valueChecker(fuel);
            valueChecker(consumption);

            if (fuel >= consumption) {
                return true;
            }
            throw new CommandException("Топлива недостаточно");
        }
        throw new CommandException("Настройки игры не установлены");
    }

}
