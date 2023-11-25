package ru.netology.ChatServer;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.ChatUser.Client;

public class TestLogger {
    @Test
    public void testLogger_correctlyLogging() {
        String example = "Test string";
        String logPath = Server.LOGPATH;
        Client client = Mockito.mock(Client.class);
        Assertions.assertTrue(Logger.log(logPath, example, client));
    }
}
