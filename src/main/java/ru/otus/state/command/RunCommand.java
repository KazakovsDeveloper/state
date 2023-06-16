package ru.otus.state.command;

import ru.otus.state.StartApp;

/**
 * Написать команду, которая стартует код, написанный в пункте 1 в отдельном потоке.
 */
public class RunCommand implements Command {

    private final StartApp startApp;

    public RunCommand(StartApp startApp) {
        this.startApp = startApp;
    }

    @Override
    public boolean execute() {
        Thread thread = new Thread(startApp::execute);
        thread.start();
        return true;
    }
}
