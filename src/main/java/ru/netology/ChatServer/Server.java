package ru.netology.ChatServer;

import ru.netology.ChatUser.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
    static final String ADDRESS = "localhost";
    static final int PORT = 8080;
    static final String SETTINGSPATH = "settings.txt";
    static final String LOGPATH = "log.txt";
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
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        out.println("Enter your name:");
                        Client client = new Client(clientSocket, out);
                        client.setName(in.readLine());
                        clients.put((client.getName() + "#" + clientSocket.getPort()), client);
                        sendMsgToAll(client.getName() + " connected!", client);
                        waitMsg(clientSocket, client);
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

    public static void sendMsgToAll(String msg, Client client) {
        for (Map.Entry<String, Client> entryMap : clients.entrySet()) {
            if (entryMap.getValue() == client) continue;
            entryMap.getValue().sendMsg(msg);
        }
        Logger.log(LOGPATH, msg, client);
    }

    public static void waitMsg(Socket clientSocket, Client client) {
        try (Scanner scanner = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                if (scanner.hasNext()) {
                    String str = scanner.nextLine();
                    if (str.equalsIgnoreCase("/exit")) {
                        sendMsgToAll(client.getName() + ": Disconnected", client);
                        break;
                    }
                    sendMsgToAll(client.getName() + ": " + str, client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}