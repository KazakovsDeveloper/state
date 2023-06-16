package ru.otus.state.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.state.command.MoveCommand;
import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest {

    private MoveCommand moveCommand;
    private GameSetting gameSetting;
    private MoveCommand moveCommandWithNullPosition;
    private MoveCommand moveCommandWithNullVelocity;
    private MoveCommand moveCommandWithNotCorrectValue;

    @BeforeEach
    public void init() {
        Vector position = new Vector(12, 5);
        Vector velocity = new Vector(-7, 3);

        gameSetting = new GameSetting(0.0, 0.0, position, velocity);
        moveCommand = new MoveCommand(gameSetting);

        GameSetting gameSettingWithNullPosition = new GameSetting(0.0, 0.0, null, velocity);
        moveCommandWithNullPosition = new MoveCommand(gameSettingWithNullPosition);

        GameSetting gameSettingWithNullVelocity = new GameSetting(0.0, 0.0, position, null);
        moveCommandWithNullVelocity = new MoveCommand(gameSettingWithNullVelocity);

        Vector velocity2 = new Vector(0, 0);
        GameSetting gameSettingWithNotCorrectVelocity = new GameSetting(0.0, 0.0, position, velocity2);
        moveCommandWithNotCorrectValue = new MoveCommand(gameSettingWithNotCorrectVelocity);
    }

    /**
     * Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3)
     * движение меняет положение объекта на (5, 8)
     */
    @Test
    @DisplayName("движение меняет положение объекта на (5, 8)")
    public void executeTestShouldMoveTheObject() {
        boolean executeMove = moveCommand.execute();

        assertTrue(executeMove);
        assertEquals(5, gameSetting.getPosition().getX());
        assertEquals(8, gameSetting.getPosition().getY());
    }

    /**
     * Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке
     */
    @Test
    @DisplayName("ошибка, невозможно прочитать положение в пространстве")
    public void executeTestShouldThrowExceptionIfCanNotReadPosition() {

        assertThrows(CommandException.class, () -> moveCommandWithNullPosition.execute());

    }

    /**
     * Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости,
     * приводит к ошибке
     */
    @Test
    @DisplayName("ошибка, невозможно прочитать скорость")
    public void executeTestShouldThrowExceptionIfCanNotReadVelocity() {

        assertThrows(CommandException.class, () -> moveCommandWithNullVelocity.execute());

    }

    /**
     * Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке
     */
    @Test
    @DisplayName("ошибка, невозможно изменить положение в пространстве")
    public void executeTestShouldThrowExceptionIfNotCorrectVelocity() {

        assertThrows(CommandException.class, () -> moveCommandWithNotCorrectValue.execute());

    }
}