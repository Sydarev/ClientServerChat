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

    private static BufferedReader out;
    private static PrintWriter in;


    public static void main(String[] args) {
        getAddress(settingsPath);
        try (Socket clientSocket = new Socket(address, port)) {
        out = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        in = new PrintWriter(clientSocket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean flag = new AtomicBoolean(false);
            new Thread(() -> {
                while (true) {
                    if (flag.get()) {
                        try {
                            out.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    try {
                        if (out.ready()) {
                            String msg = out.readLine();
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            new Thread(() -> {
                String str;
                while (true) {
                    if (scanner.hasNext()) {
                        str = scanner.nextLine();
                        if (str.equalsIgnoreCase("/exit")) {
                            in.println(str);
                            scanner.close();
                            in.close();
                            flag.set(true);
                            break;
                        }
                        in.println(str);
                    }
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
