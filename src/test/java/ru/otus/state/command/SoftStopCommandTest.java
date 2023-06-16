package ru.otus.state.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.state.command.SoftStopCommand;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SoftStopCommandTest {

    private ExecutorService executorService;
    private SoftStopCommand softStopCommand;

    @BeforeAll
    public void init() {
        executorService = mock(ExecutorService.class);
        softStopCommand = new SoftStopCommand();
    }

    @Test
    @DisplayName("проверяем, что shutdown вызван у executorService")
    public void testSoftStop() {
        softStopCommand.softStop(executorService);

        verify(executorService).shutdown();
    }
}
