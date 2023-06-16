package ru.otus.state.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ru.otus.state.command.MacroCommand;
import ru.otus.state.command.StartCommand;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StartCommandTest {

    private long id1;
    private long id2;
    private MacroCommand macroCommand;
    private StartCommand startCommand;
    private CountDownLatch latch;

    @BeforeAll
    public void init() {
        macroCommand = mock(MacroCommand.class);
        startCommand = new StartCommand(macroCommand);
        latch = new CountDownLatch(1);
    }

    @Test
    @DisplayName("проверяем, что StartCommand работает")
    public void testExecute() throws InterruptedException {

        Mockito.doAnswer(invocation -> {
            System.out.println("Выполнение макрокоманды в потоке: " + Thread.currentThread().getId());
            id1 = Thread.currentThread().getId();
            latch.countDown(); // Уменьшаем значение CountDownLatch при выполнении метода execute()
            return true;
        }).when(macroCommand).execute();

        startCommand.execute();
        id2 = Thread.currentThread().getId();

        // Ожидаем выполнение метода execute() в новом потоке
        latch.await();

        verify(macroCommand).execute();
        assertEquals(0, latch.getCount());
        assertNotEquals(id1, id2);
    }
}