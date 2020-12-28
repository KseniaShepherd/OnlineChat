package server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class SocketHandler extends Thread {

    private List<SocketHandler> list;
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private Logger logger;

    public SocketHandler(Socket socket, List<SocketHandler> list, Logger logger) throws IOException {
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
                    for (SocketHandler vr :list) {
                        vr.send(word);
                    }
                }
            } catch (NullPointerException e) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            this.downService();
        }
    }

    private void send(String msg) {
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
                for (SocketHandler vr : list) {
                    if (vr.equals(this)) vr.interrupt();
                    list.remove(this);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}