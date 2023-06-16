package ru.otus.state.command;

/**
 * Написать команду, которая стартует код, написанный в пункте 1 в отдельном потоке.
 */
public class StartCommand implements Command {

    private final MacroCommand macroCommand;

    public StartCommand(MacroCommand macroCommand) {
        this.macroCommand = macroCommand;
    }

    @Override
    public boolean execute() {
        Thread thread = new Thread(macroCommand::execute);
        thread.start();
        return true;
    }
}
