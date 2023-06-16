package ru.otus.state.model;

public class GameSetting {

    private double fuel;

    private final double consumption;

    private Vector velocity;

    private Vector position;

    private Direction direction;

    private int directionsNumber;

    private int angularVelocity;

    public GameSetting(double fuel, double consumption, Vector position, Vector velocity) {
        this.fuel = fuel;
        this.consumption = consumption;
        this.position = position;
        this.velocity = velocity;
    }

    public GameSetting(double fuel, double consumption) {
        this.fuel = fuel;
        this.consumption = consumption;
    }

    public double getFuel() {
        return fuel;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getDirectionsNumber() {
        return directionsNumber;
    }

    public void setDirectionsNumber(int directionsNumber) {
        this.directionsNumber = directionsNumber;
    }

    public int getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(int angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
}
