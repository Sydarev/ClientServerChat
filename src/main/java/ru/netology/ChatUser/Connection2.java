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

    private static BufferedReader out;
    private static PrintWriter in;


    public static void main(String[] args) {
        Scanner scanner;
        AtomicBoolean flag = new AtomicBoolean(false);
        getAddress(settingsPath);

        try (Socket clientSocket = new Socket(address, port)) {
            out = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            in = new PrintWriter(clientSocket.getOutputStream());
            scanner = new Scanner(System.in);
            new Thread(() -> {
                while (true) {
                    if (flag.get()) {
                        break;
                    }
                    try {
                        if(out.ready()) {
                            System.out.println(out.readLine());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            new Thread(() -> {
                String str;
                while(true){
                    str = scanner.nextLine();
                    in.println(str);
                    if(str.equalsIgnoreCase("/exit")) {
                        flag.set(true);
                        break;
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