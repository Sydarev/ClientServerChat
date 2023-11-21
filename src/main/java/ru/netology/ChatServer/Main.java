package ru.netology.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 8080;
    private static final String SETTINGSPATH = "settings.txt";

    public static void main(String[] args) {
        try {
            createSettings(SETTINGSPATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                System.out.println("client " + clientSocket.getPort() + " connected");
                out.println("Welcome to the server! What's your name?");
                String clientResponce = in.readLine();
                out.println("Nice to meet you, "+clientResponce);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void checkingSettings(File file) {
        String str;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
}