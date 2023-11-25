package ru.netology.ChatUser;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestConnections {
    @Test
    public void testClient_correctlyGetAddress() {
        String filePath = "settings.txt";
        Assertions.assertTrue(Connection1.getAddress(filePath));
        Assertions.assertTrue(Connection2.getAddress(filePath));
    }
}
