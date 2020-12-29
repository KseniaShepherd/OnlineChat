package server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class MessageExchanger extends Thread {

    private List<MessageExchanger> list;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private Logger logger;

    public MessageExchanger(Socket socket, List<MessageExchanger> list, Logger logger) throws IOException {
        this.list = list;
        this.socket = socket;
        this.logger = logger;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        String word;
        try {
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            try {
                while (true) {
                    word = in.readLine();
                    this.logger.info(word);
                    if ("exit".equals(word)) {
                        this.downService();
                        break;
                    }
                    System.out.println(word);
                    for (MessageExchanger exchanger :list) {
                        exchanger.send(word);
                    }
                }
            } catch (NullPointerException e) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            this.downService();
        }
    }

    public void send(String msg) {
        System.out.println("Method send is called with message: " + msg);
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (MessageExchanger exchanger : list) {
                    if (exchanger.equals(this)) {
                        exchanger.interrupt();
                    }
                    list.remove(this);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}