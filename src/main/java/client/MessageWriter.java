package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageWriter extends Thread {
    private Client client;
    private BufferedWriter out;
   private BufferedReader inputUser;
    private String name;
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;

    public MessageWriter(BufferedWriter out, BufferedReader inputUser, String name, Client client) {
        this.out = out;
        this.inputUser = inputUser;
        this.name = name;
        this.time = new Date();
        this.dt1 = new SimpleDateFormat("HH:mm:ss");
        this.dtime = dt1.format(time);
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            String userWord;
            try {

                time = new Date();
                dt1 = new SimpleDateFormat("HH:mm:ss");
                dtime = dt1.format(time);
                System.out.println("Введите сообщение:");
                userWord = inputUser.readLine();
                client.getLogger().info(userWord);
                if ("exit".equals(userWord)) {
                    out.write("exit" + "\n");
                    client.downService();
                    break;
                } else {
                    out.write("(" + dtime + ") " + name + ": " + userWord + "\n");
                }
                out.flush();
            } catch (IOException e) {
                client.downService();
            }
        }
    }
}