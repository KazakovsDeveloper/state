package ru.otus.state.command;

import java.util.concurrent.ExecutorService;

/**
 * Написать команду, которая останавливает цикл выполнения команд из пункта 1,
 * только после того, как все команды завершат свою работу (soft stop).
 */
public class SoftStopCommand implements Command {

    @Override
    public boolean execute() {
        return true;
    }

    public void softStop(ExecutorService executor) {
        executor.shutdown();
    }
}
