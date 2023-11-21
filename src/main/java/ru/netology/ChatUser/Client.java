package ru.netology.ChatUser;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    static String address;
    static int port;
    static String settingsPath = "settings.txt";
    public static void main(String[] args) {
        getAddress(settingsPath);
        try (Socket clientSocket = new Socket(address, port);
             PrintWriter in = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader out = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println(out.readLine());
            String request;
            while(true) {
                request = scanner.nextLine();
                if (request.toLowerCase().equals("exit")) {
                    in.println("The client was disconnected");
                    break;
                }
                in.println(request);
                System.out.println(out.readLine());
                }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAddress(String filePath){
        String[] strings;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            strings = br.readLine().split(" ");
            address = strings[strings.length-1];
            strings = br.readLine().split(" ");
            port = Integer.parseInt(strings[strings.length-1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
