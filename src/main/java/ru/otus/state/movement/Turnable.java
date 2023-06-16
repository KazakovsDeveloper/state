package ru.otus.state.movement;

import ru.otus.state.model.Direction;

public interface Turnable {

    void turn();

    Direction getDirection();

    int getAngularVelocity();

    void setDirection (Direction newV);

}
