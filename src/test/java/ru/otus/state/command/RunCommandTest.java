package ru.otus.state.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ru.otus.state.StartApp;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RunCommandTest {

    private long id1;
    private long id2;
    private StartApp startApp;
    private RunCommand runCommand;
    private CountDownLatch latch;

    @BeforeAll
    public void init() {
        startApp = mock(StartApp.class);
        runCommand = new RunCommand(startApp);
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
        }).when(startApp).execute();

        runCommand.execute();
        id2 = Thread.currentThread().getId();

        // Ожидаем выполнение метода execute() в новом потоке
        latch.await();

        verify(startApp).execute();
        assertEquals(0, latch.getCount());
        assertNotEquals(id1, id2);
    }
}