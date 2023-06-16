package ru.otus.state.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.state.command.CheckFuelCommand;
import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckFuelCommandTest {

    private CheckFuelCommand checkFuelCommandWithSettings;
    private CheckFuelCommand checkFuelCommandWithoutSettings;
    private CheckFuelCommand checkFuelCommandWithGameSettingIsNull;
    private CheckFuelCommand checkFuelCommandWithNegativeFuel;
    private CheckFuelCommand checkFuelCommandWithNaNFuel;
    private CheckFuelCommand checkFuelCommandWithInfinityFuel;


    @BeforeAll
    public void init() {
        GameSetting gameSetting = new GameSetting(10.0, 2.0);
        GameSetting gameSetting2 = new GameSetting(0.0, 2.0);
        GameSetting gameSetting3 = new GameSetting(-6.0, 2.0);
        GameSetting gameSetting4 = new GameSetting(NaN, 2.0);
        GameSetting gameSetting5 = new GameSetting(NEGATIVE_INFINITY, 2.0);

        checkFuelCommandWithSettings = new CheckFuelCommand(gameSetting);
        checkFuelCommandWithoutSettings = new CheckFuelCommand(gameSetting2);
        checkFuelCommandWithGameSettingIsNull = new CheckFuelCommand(null);
        checkFuelCommandWithNegativeFuel = new CheckFuelCommand(gameSetting3);
        checkFuelCommandWithNaNFuel = new CheckFuelCommand(gameSetting4);
        checkFuelCommandWithInfinityFuel = new CheckFuelCommand(gameSetting5);
    }

    @Test
    @DisplayName("проверка, что топлива достаточно для движения")
    public void executeTestFuelBiggerThanConsumption() {
        boolean fuelIsBiggerThan0 = checkFuelCommandWithSettings.execute();

        assertTrue(fuelIsBiggerThan0);
    }

    @Test
    @DisplayName("проверка, что топлива недостаточно для движения")
    public void executeTestThrowsCommandException() {

        assertThrows(CommandException.class, () -> checkFuelCommandWithoutSettings.execute());
    }

    @Test
    @DisplayName("проверка, что исключение выброшено при null GameSetting")
    public void executeTestThrowsCommandExceptionIfGameSettingIsNull() {

        assertThrows(CommandException.class, () -> checkFuelCommandWithGameSettingIsNull.execute());
    }

    @Test
    @DisplayName("проверка, что исключение выброшено при отрицательном fuel")
    public void executeTestThrowsExceptionIfFuelIsNegative() {

        assertThrows(RuntimeException.class, () -> checkFuelCommandWithNegativeFuel.execute());
    }

    @Test
    @DisplayName("проверка, что исключение выброшено при fuel NaN")
    public void executeTestThrowsExceptionIfFuelIsNaN() {

        assertThrows(RuntimeException.class, () -> checkFuelCommandWithNaNFuel.execute());
    }

    @Test
    @DisplayName("проверка, что исключение выброшено при fuel Infinity")
    public void executeTestThrowsExceptionIfFuelIsInfinity() {

        assertThrows(RuntimeException.class, () -> checkFuelCommandWithInfinityFuel.execute());
    }

}