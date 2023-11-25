package ru.netology.ChatUser;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class Connection2 {
    private static String address;
    private static int port;
    private static String settingsPath = "settings.txt";

    private static BufferedReader in;
    private static PrintWriter out;
    private static Socket clientSocket;


    public static void main(String[] args) {
        getAddress(settingsPath);
        try {
            clientSocket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            AtomicBoolean flag = new AtomicBoolean(true);
            new Thread(() -> {
                while (true) {
                    if (!flag.get()) {
                        try {
                            in.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    try {
                        if (in.ready()) {
                            String msg = in.readLine();
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            new Thread(() -> {
                while (true) {
                    if (scanner.hasNext()) {
                        String str = scanner.nextLine();
                        if (str.equalsIgnoreCase("/exit")) {
                            out.println(str);
                            scanner.close();
                            out.close();
                            flag.set(false);
                            break;
                        }
                        out.println(str);
                    }
                }
            }).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getAddress(String filePath) {
        String[] strings;
        try {
            in = new BufferedReader(new FileReader(filePath));
            strings = in.readLine().split(" ");
            address = strings[strings.length - 1];
            strings = in.readLine().split(" ");
            port = Integer.parseInt(strings[strings.length - 1]);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}