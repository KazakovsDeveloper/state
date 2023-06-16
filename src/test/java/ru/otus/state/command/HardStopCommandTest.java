package ru.otus.state.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.state.command.HardStopCommand;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HardStopCommandTest {

    private ExecutorService executorService;
    private HardStopCommand hardStopCommand;

    @BeforeAll
    public void init() {
        executorService = mock(ExecutorService.class);
        hardStopCommand = new HardStopCommand();
    }

    @Test
    @DisplayName("проверяем, что shutdownNow вызван у executorService")
    public void testSoftStop() {
        hardStopCommand.hardStop(executorService);

        verify(executorService).shutdownNow();
    }

}