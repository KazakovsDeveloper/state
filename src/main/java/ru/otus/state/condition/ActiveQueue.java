package ru.otus.state.condition;

/**
 * модель для обозначения активной очереди в данный момент
 */
public class ActiveQueue {

    private boolean firstActive;

    private boolean secondActive;

    public ActiveQueue(boolean firstActive, boolean secondActive) {
        this.firstActive = firstActive;
        this.secondActive = secondActive;
    }

    public ActiveQueue() {
    }

    public boolean isFirstActive() {
        return firstActive;
    }

    public void setFirstActive(boolean firstActive) {
        this.firstActive = firstActive;
    }

    public boolean isSecondActive() {
        return secondActive;
    }

    public void setSecondActive(boolean secondActive) {
        this.secondActive = secondActive;
    }
}
