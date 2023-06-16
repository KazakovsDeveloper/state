package ru.otus.state.condition;

import ru.otus.state.command.Command;
import ru.otus.state.command.HardStopCommand;
import ru.otus.state.utils.Utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * MoveTo - состояние, в котором команды извлекаются из очереди и перенаправляются в другую очередь.
 * Такое состояние может быть полезно, если хотите разгрузить сервер перед предстоящим его выключением.
 * Обработка команды HardStop приводит к тому, что будет возвращена "нулевая ссылка" на следующее состояние, что соответствует завершению работы потока.
 * Обработка команды RunCommand приводит к тому, что будет возвращена ссылка на "обычное" состояние.
 */
public class MoveToState implements State {

    private final ExecutorService executor;

    public MoveToState(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public State handle(Context context) {
        ConcurrentLinkedQueue<Command> currentActiveQueue = Utils.getActiveQueue(context);
        ConcurrentLinkedQueue<Command> futureActiveQueue = Utils.getNotActiveQueue(context);
        // добавляем активную в неактивную
        futureActiveQueue.addAll(currentActiveQueue);
        // удаляем команды из неактивной
        currentActiveQueue.removeAll(futureActiveQueue);
        // переключаем активность очередей
        Utils.setFlagsToQueues(context, currentActiveQueue, futureActiveQueue);
        while (!futureActiveQueue.isEmpty()) {
            Command command = futureActiveQueue.poll();
            if (command != null) {
                if ("HardStopCommand".equals(command.getClass().getSimpleName())) {
                    HardStopCommand hardStopCommand = (HardStopCommand) command;
                    hardStopCommand.hardStop(executor);
                    return null;
                } else if ("RunCommand".equals(command.getClass().getSimpleName())) {
                    System.out.println("переключились на simple state");
                    return new SimpleState(executor);
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
        throw new RuntimeException("Команд нет");
    }
}
