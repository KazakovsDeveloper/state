package ru.otus.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.state.command.*;
import ru.otus.state.condition.ActiveQueue;
import ru.otus.state.condition.Context;
import ru.otus.state.model.GameSetting;
import ru.otus.state.model.Vector;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StartAppTest {

    private ConcurrentLinkedQueue<Command> firstCommands;
    private ConcurrentLinkedQueue<Command> secondCommands;
    private StartApp startApp;
    private Context context;

    @BeforeEach
    public void init() {
        firstCommands = new ConcurrentLinkedQueue<>();
        secondCommands = new ConcurrentLinkedQueue<>();
        ActiveQueue activeQueue = new ActiveQueue(true, false);
        context = new Context(firstCommands, secondCommands, activeQueue);
        startApp = new StartApp(context);
        Vector position = new Vector(1, 6);
        Vector velocity = new Vector(1, 8);
        GameSetting gameSetting = new GameSetting(5.0, 1.2, position, velocity);
        Command move = new MoveCommand(gameSetting);
        Command run = new RunCommand(startApp);
        Command rotate = new RotateCommand();
        Command hard = new HardStopCommand();
        Command burn = new BurnFuelCommand(gameSetting);
        firstCommands.add(move);
        firstCommands.add(run);
        firstCommands.add(hard);
        firstCommands.add(rotate);
        firstCommands.add(burn);
    }

    @Test
    @DisplayName("запуск основного метода для смены состояний")
    public void executeTestSuccess() {
        assertDoesNotThrow(() -> startApp.execute());
    }


}