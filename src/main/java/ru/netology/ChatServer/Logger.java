package ru.netology.ChatServer;

import ru.netology.ChatUser.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static boolean log(String logPath, String msg, Client client) {
        try (FileWriter fileWriter = new FileWriter(logPath, true)) {
            fileWriter.write(client.getName() + "(" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "): " + msg + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
