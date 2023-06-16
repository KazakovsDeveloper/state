package ru.otus.state.command;

import java.util.concurrent.ExecutorService;

/**
 * Написать команду, которая останавливает цикл выполнения команд из пункта 1,
 * не дожидаясь их полного завершения (hard stop).
 */
public class HardStopCommand implements Command {

    @Override
    public boolean execute() {
        return true;
    }

    public void hardStop(ExecutorService executor) {
        executor.shutdownNow();
    }
}
