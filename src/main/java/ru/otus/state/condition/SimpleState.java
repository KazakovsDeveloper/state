package ru.otus.state.condition;

import ru.otus.state.command.Command;
import ru.otus.state.command.HardStopCommand;
import ru.otus.state.utils.Utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * "Обычное"
 * В этом состоянии очередная команда извлекается из очереди и выполняется.
 * По умолчанию возвращается ссылка на этот же экземпляр состояния.
 * Обработка команды HardStop приводит к тому, что будет возвращена "нулевая ссылка" на следующее состояние,
 * что соответствует завершению работы потока.
 * Обработка команды MoveToCommand приводит к тому, что будет возвращена ссылка на состояние MoveTo
 */
public class SimpleState implements State {

    private final ExecutorService executor;

    public SimpleState(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public State handle(Context context) {
        ConcurrentLinkedQueue<Command> activeQueue = Utils.getActiveQueue(context);
        while (!activeQueue.isEmpty()) {
            Command command = activeQueue.poll();
            if (command != null) {
                if ("HardStopCommand".equals(command.getClass().getSimpleName())) {
                    HardStopCommand hardStopCommand = (HardStopCommand) command;
                    hardStopCommand.hardStop(executor);
                    System.out.println("команда hard stop");
                    return null;
                } else if ("MoveCommand".equals(command.getClass().getSimpleName())) {
                    System.out.println("переключились на move state");
                    return new MoveToState(executor);
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
                return this;
            }
        }
        throw new RuntimeException("Команд нет");
    }
}
