package ru.otus.state.condition;

import ru.otus.state.command.Command;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * контекст для состояний
 */
public class Context {

    private ConcurrentLinkedQueue<Command> firstQueueCommands;

    private ConcurrentLinkedQueue<Command> secondQueueCommands;

    private ActiveQueue activeQueue;

    public Context(ConcurrentLinkedQueue<Command> firstQueueCommands,
                   ConcurrentLinkedQueue<Command> secondQueueCommands,
                   ActiveQueue activeQueue) {
        this.firstQueueCommands = firstQueueCommands;
        this.secondQueueCommands = secondQueueCommands;
        this.activeQueue = activeQueue;
    }

    public ActiveQueue getActiveQueue() {
        return activeQueue;
    }

    public ConcurrentLinkedQueue<Command> getFirstQueueCommands() {
        return firstQueueCommands;
    }

    public ConcurrentLinkedQueue<Command> getSecondQueueCommands() {
        return secondQueueCommands;
    }
}
