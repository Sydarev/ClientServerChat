package ru.netology.ChatServer;

import ru.netology.ChatUser.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 8080;
    private static final String SETTINGSPATH = "settings.txt";
    private static Map<String, Client> clients = new HashMap<>();


    public static void main(String[] args) {
        try {
            createSettings(SETTINGSPATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                sendMsgToAll("New connection: " + clientSocket.getPort());
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){
                        Client client = new Client(clientSocket, out);
                        clients.put((client.getName() + "#" + clientSocket.getPort()), client);
                        System.out.println(client);
                        waitMsg(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSettings(String filePath) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("settings.txt", false));
        bf.write("address: " + ADDRESS);
        bf.newLine();
        bf.write("port: " + PORT);
        System.out.println("Settings file created successfully");
        bf.close();
    }

    public static void sendMsgToAll(String msg) {
        for (Map.Entry<String, Client> entryMap : clients.entrySet()) {
            entryMap.getValue().sendMsg(entryMap.getKey()+msg);
            System.out.println("Proverka");
        }
    }

    public static void waitMsg(Socket clientSocket) {
        String str;
        try (Scanner scanner = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                if (scanner.hasNext()) {
                    str = scanner.nextLine();
                    if (str.equalsIgnoreCase("/exit")) {
                        sendMsgToAll(clientSocket.getPort() + " : Disconnected");
                        break;
                    }
                    sendMsgToAll(clientSocket.getPort() + " : " + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}