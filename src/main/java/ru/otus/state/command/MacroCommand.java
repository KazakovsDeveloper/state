package ru.otus.state.command;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MacroCommand {
    private final ConcurrentLinkedQueue<Command> commands;
    private final ExecutorService executor;

    public MacroCommand(ConcurrentLinkedQueue<Command> commands) {
        this.commands = commands;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Реализовать код, который запускается в отдельном потоке и делает следующее
     * В цикле получает из потокобезопасной очереди команду и запускает ее.
     * Выброс исключения из команды не должен прерывать выполнение потока.
     */
    public void execute() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            if (command != null) {
                if ("HardStopCommand".equals(command.getClass().getSimpleName())) {
                    HardStopCommand hardStopCommand = (HardStopCommand) command;
                    hardStopCommand.hardStop(executor);
                    break; // останавливаем выполнение цикла
                } else if ("SoftStopCommand".equals(command.getClass().getSimpleName())) {
                    SoftStopCommand softStopCommand = (SoftStopCommand) command;
                    softStopCommand.softStop(executor);
                    try {
                        boolean terminated = executor.awaitTermination(60, TimeUnit.SECONDS);
                        if (!terminated) {
                            System.err.println("ExecutorService did not terminate");
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Tasks interrupted: " + e.getMessage());
                    }
                } else {
                    executor.submit(() -> {
                        try {
                            System.out.println(command.getClass() + " ");
                            command.execute();
                        } catch (RuntimeException exception) {
                            System.out.println("Исключение: " + exception);
                        }
                    });
                }
            }
        }
    }
}