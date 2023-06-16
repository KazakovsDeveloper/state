package ru.otus.state.command;

import org.junit.jupiter.api.*;
import ru.otus.state.command.BurnFuelCommand;
import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;

import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BurnFuelCommandTest {

    private BurnFuelCommand burnFuelCommand;
    private BurnFuelCommand burnFuelCommandWithNegativeConsumption;
    private BurnFuelCommand burnFuelCommandWithNaNConsumption;
    private BurnFuelCommand burnFuelCommandWithNullGameSetting;
    private BurnFuelCommand burnFuelCommandWithInfiniteGameSetting;

    private GameSetting gameSettingWithCorrectData;

    @BeforeAll
    public void init() {
        gameSettingWithCorrectData = new GameSetting(15.0, 5.0);
        GameSetting gameSettingWithNegativeData = new GameSetting(0.0, -5.0);
        GameSetting gameSettingWithNaN = new GameSetting(0.0, NaN);
        GameSetting gameSettingWithInfinite = new GameSetting(0.0, POSITIVE_INFINITY);

        burnFuelCommand = new BurnFuelCommand(gameSettingWithCorrectData);
        burnFuelCommandWithNegativeConsumption = new BurnFuelCommand(gameSettingWithNegativeData);
        burnFuelCommandWithNullGameSetting = new BurnFuelCommand(null);
        burnFuelCommandWithNaNConsumption = new BurnFuelCommand(gameSettingWithNaN);
        burnFuelCommandWithInfiniteGameSetting = new BurnFuelCommand(gameSettingWithInfinite);
    }

    @Test
    @DisplayName("количество топлива уменьшилось")
    public void executeTestShouldReturnTrue() {
        boolean executeTrue = burnFuelCommand.execute();

        assertTrue(executeTrue);
        assertEquals(10.0, gameSettingWithCorrectData.getFuel());
    }

    @Test
    @DisplayName("GameSetting is null")
    public void executeTestThrowExceptionIfGameSettingIsNull() {

        assertThrows(CommandException.class, () -> burnFuelCommandWithNullGameSetting.execute());

    }

    @Test
    @DisplayName("передано отрицательное значение расхода")
    public void executeTestThrowExceptionWhenDataIsNegative() {

        assertThrows(RuntimeException.class, () -> burnFuelCommandWithNegativeConsumption.execute());

    }

    @Test
    @DisplayName("передано NaN для расхода")
    public void executeTestThrowExceptionWhenDataIsNan() {

        assertThrows(RuntimeException.class, () -> burnFuelCommandWithNaNConsumption.execute());

    }

    @Test
    @DisplayName("передано Infinite для расхода")
    public void executeTestThrowExceptionWhenDataIsInfinite() {

        assertThrows(RuntimeException.class, () -> burnFuelCommandWithInfiniteGameSetting.execute());

    }
}