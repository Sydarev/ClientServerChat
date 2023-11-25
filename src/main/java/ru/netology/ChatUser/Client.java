package ru.netology.ChatUser;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String name;
    private Socket socket;
    private PrintWriter out;

    public Client(Socket socket, PrintWriter out) {
        this.socket = socket;
        this.out = out;
    }

    public void sendMsg(String msg) {
        out.println(msg);
        out.flush();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
