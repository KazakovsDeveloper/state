package ru.otus.state.condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.state.command.Command;
import ru.otus.state.command.MoveCommand;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleStateTest {

    private SimpleState simpleState;
    private Context context;
    private Context context2;

    @BeforeEach
    public void init() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        simpleState = new SimpleState(executor);
        ConcurrentLinkedQueue<Command> firstCommands = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Command> secondCommands = new ConcurrentLinkedQueue<>();
        GameSetting gameSetting = new GameSetting(1.0, 8.0, new Vector(1, 5), new Vector(9, 3));
        firstCommands.add(new MoveCommand(gameSetting));
        context = new Context(firstCommands, secondCommands, new ActiveQueue(true, false));
        context2 =
                new Context(new ConcurrentLinkedQueue<>(), new ConcurrentLinkedQueue<>(), new ActiveQueue(true, false));
    }

    @Test
    @DisplayName("после команды MoveToCommand, поток переходит на обработку Команд с помощью состояния MoveTo")
    public void simpleStateTestReturnMoveToState() {
        State handle = simpleState.handle(context);
        assertEquals("MoveToState", handle.getClass().getSimpleName());
    }

    @Test
    @DisplayName("выбрасывает исключение, если очередь пустая")
    public void simpleStateTestThrowsException() {
        assertThrows(RuntimeException.class, () -> simpleState.handle(context2));
    }
}