package ru.otus.state.condition;

/**
 * интерфейс состояний
 */
public interface State {

    State handle(Context context);

}
