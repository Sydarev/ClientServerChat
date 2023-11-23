package ru.netology.ChatUser;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class Connection1 {
    private static String address;
    private static int port;
    private static String settingsPath = "settings.txt";
    private static Scanner scanner;
    private static BufferedReader out;
    private static PrintWriter in;
    private static AtomicBoolean flag = new AtomicBoolean(false);

    public static void main(String[] args) {
        getAddress(settingsPath);
        try (Socket clientSocket = new Socket(address, port)) {
            out = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
            in = new PrintWriter(clientSocket.getOutputStream());
            scanner = new Scanner(System.in);
            new Thread(() -> {
                while (true) {
                    if (flag.get()) {

                    }
                }
            }).start();
            new Thread(() -> {
                try () {

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAddress(String filePath) {
        String[] strings;
        try {
            out = new BufferedReader(new FileReader(filePath));
            strings = out.readLine().split(" ");
            address = strings[strings.length - 1];
            strings = out.readLine().split(" ");
            port = Integer.parseInt(strings[strings.length - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
