package ru.otus.state.command;

import ru.otus.state.exception.CommandException;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;
import ru.otus.state.movement.Movable;

import static java.util.Objects.isNull;

public class MoveCommand implements Command, Movable {

    private final GameSetting gameSetting;

    public MoveCommand(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    @Override
    public boolean execute() {
        move();
        return true;
    }

    @Override
    public void move() {
        Vector position = getPosition();
        Vector velocity = getVelocity();

        if (isNull(position) || isNull(velocity)) {
            throw new CommandException("Не указаны координаты позиции");
        }

        Vector newPosition = getVectorSum(position, velocity);

        gameSetting.setPosition(newPosition);
    }

    private Vector getVectorSum(Vector position, Vector velocity) {
        int xPosition = position.getX();
        int xVelocity = velocity.getX();

        int yPosition = position.getY();
        int yVelocity = velocity.getY();

        if (xVelocity == 0 && yVelocity == 0) {
            throw new CommandException("Объект не сдвинут, значения скорости " + xVelocity + " " + yVelocity);
        }

        int xNewPosition = xPosition + xVelocity;
        int yNewPosition = yPosition + yVelocity;

        return new Vector(xNewPosition, yNewPosition);
    }

    @Override
    public Vector getPosition() {
        return gameSetting.getPosition();
    }

    @Override
    public Vector getVelocity() {
        return gameSetting.getVelocity();
    }
}
