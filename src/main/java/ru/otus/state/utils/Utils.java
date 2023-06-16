package ru.otus.state.utils;

import ru.otus.state.command.Command;
import ru.otus.state.condition.Context;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * утильный класс для работы с контекстом и очередями
 */
public class Utils {

    public static ConcurrentLinkedQueue<Command> getActiveQueue(Context context) {
        if (context.getActiveQueue().isFirstActive()) {
            return context.getFirstQueueCommands();
        } else if (context.getActiveQueue().isSecondActive()) {
            return context.getSecondQueueCommands();
        } else {
            throw new RuntimeException("Ни одна из очередей не отмечена как активная");
        }
    }

    public static ConcurrentLinkedQueue<Command> getNotActiveQueue(Context context) {
        if (!context.getActiveQueue().isFirstActive()) {
            return context.getFirstQueueCommands();
        } else if (!context.getActiveQueue().isSecondActive()) {
            return context.getSecondQueueCommands();
        } else {
            throw new RuntimeException("Ни одна из очередей не отмечена как неактивная");
        }
    }

    public static void setFlagsToQueues(Context context, ConcurrentLinkedQueue<Command> currentActiveQueue, ConcurrentLinkedQueue<Command> futureActiveQueue) {
        String nameOfCurrentActiveQueue = getNameOfQueue(context, currentActiveQueue);
        String nameOfFutureActiveQueue = getNameOfQueue(context, futureActiveQueue);
        switchFlagOfCurrentActiveQueue(context, nameOfCurrentActiveQueue);
        switchFlagOfFutureActiveQueue(context, nameOfFutureActiveQueue);
    }

    private static String getNameOfQueue(Context context, ConcurrentLinkedQueue<Command> queue) {
        if (queue.equals(context.getFirstQueueCommands())) {
            return "first";
        } else {
            return "second";
        }
    }

    private static void switchFlagOfCurrentActiveQueue(Context context, String nameOfCurrentActiveQueue) {
        if ("first".equals(nameOfCurrentActiveQueue)) {
            context.getActiveQueue().setFirstActive(false);
        } else {
            context.getActiveQueue().setSecondActive(false);
        }
    }

    private static void switchFlagOfFutureActiveQueue(Context context, String nameOfFutureActiveQueue) {
        if ("first".equals(nameOfFutureActiveQueue)) {
            context.getActiveQueue().setFirstActive(true);
        } else {
            context.getActiveQueue().setSecondActive(true);
        }
    }
}
