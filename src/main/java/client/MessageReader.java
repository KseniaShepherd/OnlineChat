package client;


import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader extends Thread {
    private BufferedReader in;
    private Client client;

    public MessageReader(BufferedReader in, Client client) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        String str;
        try {
            while (true) {
                str = in.readLine();
                client.getLogger().info(str);
                if ("exit".equals(str)) {
                    client.downService();
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            client.downService();
        }
    }
}
