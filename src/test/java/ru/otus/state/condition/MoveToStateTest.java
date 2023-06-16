package ru.otus.state.condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.state.StartApp;
import ru.otus.state.command.*;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class MoveToStateTest {

    private MoveToState moveToState;
    private Context context;
    private Context context2;
    private ConcurrentLinkedQueue<Command> firstCommands;
    private ExecutorService executor;

    @BeforeEach
    public void init() {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        moveToState = new MoveToState(executor);
        firstCommands = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Command> secondCommands = new ConcurrentLinkedQueue<>();
        StartApp startApp = new StartApp(context);
        firstCommands.add(new RunCommand(startApp));
        context = new Context(firstCommands, secondCommands, new ActiveQueue(true, false));
        ConcurrentLinkedQueue<Command> firstCont2 = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Command> secondCont2 = new ConcurrentLinkedQueue<>();
        GameSetting gameSetting = new GameSetting(3.0, 6.0, new Vector(1, 8), new Vector(9, 3));
        secondCont2.add(new RotateCommand());
        secondCont2.add(new CheckFuelCommand(gameSetting));
        secondCont2.add(new HardStopCommand());
        secondCont2.add(new TurnCommand(gameSetting));
        context2 = new Context(firstCont2, secondCont2, new ActiveQueue(false, true));
    }

    @Test
    @DisplayName("после команды RunCommand, поток переходит на обработку Команд с помощью состояния \"Обычное\"")
    public void moveToStateTestReturnSimpleState() {
        State handle = moveToState.handle(context);
        assertEquals("SimpleState", handle.getClass().getSimpleName());
    }

    @Test
    @DisplayName("после команды hard stop, поток завершается")
    public void moveToStateTestReturnNull() {
        State handle = moveToState.handle(context2);
        assertNull(handle);
        assertTrue(executor.isShutdown());
    }

    @Test
    @DisplayName("проверка, что из очереди удалились команды и поменялся ее флаг")
    public void firstCommandsTestIsEmptyAndHasFlagFalse() {
        moveToState.handle(context);
        assertTrue(firstCommands.isEmpty());
        assertFalse(context.getActiveQueue().isFirstActive());
    }


}