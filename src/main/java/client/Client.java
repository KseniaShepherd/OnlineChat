package client;

import utils.LoggerUtil;
import utils.Util;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String nickname;
    private Logger logger;

    public Client(String ip, int port) {
        String uuid = UUID.randomUUID().toString();
        logger = LoggerUtil.createLogger(uuid, uuid + ".log");
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.chooseNickname();
            new MessageReader(in, this).start();
            new MessageWriter(out, inputUser, nickname, this).start();
        } catch (IOException e) {
            this.downService();
        }
    }

    public Logger getLogger() {
        return logger;
    }

    private void chooseNickname() {
        System.out.print("Choose your nick: ");
        try {
            nickname = inputUser.readLine();
            out.write("Hello " + nickname + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    void downService() {
        try {
            if (this.socket.isClosed()) {
                this.socket.close();
                this.in.close();
                this.out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        try {
            Properties settings = Util.loadProperties("src/main/resources/settings.properties");
            String port = settings.getProperty("port");
            String ip = settings.getProperty("ip");
            new Client(ip, Integer.parseInt(port));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
