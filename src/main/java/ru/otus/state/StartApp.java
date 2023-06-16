package ru.otus.state;

import ru.otus.state.condition.State;
import ru.otus.state.condition.Context;
import ru.otus.state.condition.SimpleState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.nonNull;

public class StartApp {
    private final Context context;
    private final ExecutorService executor;
    private State state;

    public StartApp(Context context) {
        this.context = context;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.state = new SimpleState(executor);
    }

    public void execute() {
        while (nonNull(state)) {
            State newState = next(state);
            setCommandState(newState);
        }
    }

    public void setCommandState(State state) {
        this.state = state;
    }

    public State next(State state) {
        return state.handle(context);
    }
}