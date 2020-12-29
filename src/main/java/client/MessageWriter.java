package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageWriter extends Thread {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private Client client;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String name;

    public MessageWriter(BufferedWriter out, BufferedReader inputUser, String name, Client client) {
        this.out = out;
        this.inputUser = inputUser;
        this.name = name;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            String userWord;
            try {
                String dtime = SIMPLE_DATE_FORMAT.format(new Date());
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