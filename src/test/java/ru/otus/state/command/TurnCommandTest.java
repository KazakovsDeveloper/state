package ru.otus.state.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.state.command.TurnCommand;
import ru.otus.state.exception.CommandException;
import ru.otus.state.model.Direction;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TurnCommandTest {

    private TurnCommand turnCommand;

    private TurnCommand turnCommandWithDirection0;

    private TurnCommand turnCommandWithDirectionNull;

    private TurnCommand turnCommandWithVelocity0;

    private TurnCommand turnCommandWithDirectionsNumber0;

    @BeforeEach
    public void init() {
        Vector position = new Vector(1, 5);
        Vector velocity = new Vector(3, 6);
        Direction direction = new Direction(3);
        Direction direction0 = new Direction(0);

        GameSetting gameSetting = new GameSetting(10.0, 2.0, position, velocity);
        gameSetting.setDirection(direction);
        gameSetting.setDirectionsNumber(45);
        gameSetting.setAngularVelocity(4);

        GameSetting gameSettingWithDirection0 = new GameSetting(10.0, 2.0, position, velocity);
        gameSettingWithDirection0.setDirection(direction0);
        gameSettingWithDirection0.setDirectionsNumber(45);
        gameSettingWithDirection0.setAngularVelocity(4);

        GameSetting gameSettingWithDirectionNull = new GameSetting(10.0, 2.0, position, velocity);
        gameSettingWithDirectionNull.setDirection(null);
        gameSettingWithDirectionNull.setDirectionsNumber(45);
        gameSettingWithDirectionNull.setAngularVelocity(4);

        GameSetting gameSettingWithVelocity0 = new GameSetting(10.0, 2.0, position, velocity);
        gameSettingWithVelocity0.setDirection(direction);
        gameSettingWithVelocity0.setDirectionsNumber(45);
        gameSettingWithVelocity0.setAngularVelocity(0);

        GameSetting gameSettingWithDirectionsNumber0 = new GameSetting(10.0, 2.0, position, velocity);
        gameSettingWithDirectionsNumber0.setDirection(direction);
        gameSettingWithDirectionsNumber0.setDirectionsNumber(0);
        gameSettingWithDirectionsNumber0.setAngularVelocity(5);

        turnCommand = new TurnCommand(gameSetting);
        turnCommandWithDirection0 = new TurnCommand(gameSettingWithDirection0);
        turnCommandWithDirectionNull = new TurnCommand(gameSettingWithDirectionNull);
        turnCommandWithVelocity0 = new TurnCommand(gameSettingWithVelocity0);
        turnCommandWithDirectionsNumber0 = new TurnCommand(gameSettingWithDirectionsNumber0);
    }

    @Test
    @DisplayName("объект поворачивается")
    public void executeTestShouldSuccess() {

        boolean execute = turnCommand.execute();
        assertTrue(execute);

    }

    @Test
    @DisplayName("выкидывается исключение, если направление 0")
    public void executeTestShouldThrowExceptionIfDirection0() {

        assertThrows(CommandException.class, () -> turnCommandWithDirection0.execute());

    }

    @Test
    @DisplayName("выкидывается исключение, если направление не установлено")
    public void executeTestShouldThrowExceptionIfDirectionNull() {

        assertThrows(CommandException.class, () -> turnCommandWithDirectionNull.execute());

    }

    @Test
    @DisplayName("выкидывается исключение, если скорость 0")
    public void executeTestShouldThrowExceptionIfVelocity0() {

        assertThrows(CommandException.class, () -> turnCommandWithVelocity0.execute());

    }

    @Test
    @DisplayName("выкидывается исключение, если градус направления 0")
    public void executeTestShouldThrowExceptionIfDirectionsNumber() {

        assertThrows(CommandException.class, () -> turnCommandWithVelocity0.execute());

    }

}