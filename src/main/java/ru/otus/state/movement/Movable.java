package ru.otus.state.movement;

import ru.otus.state.model.Vector;

public interface Movable {

    void move();

    Vector getPosition();

    Vector getVelocity();

}
