package server;

import utils.LoggerUtil;
import utils.Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class Server {
    private static final String PORT = "port";
    private static final String SETTINGS_FILE = "src/main/resources/settings.properties";

    private static List<MessageExchanger> messageExchangers = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        Properties settings = Util.loadProperties(SETTINGS_FILE);
        String port = settings.getProperty(PORT);
        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        Logger logger = LoggerUtil.createLogger("ServerLog", "ServerLog.log");
        logger.info("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    MessageExchanger messageExchanger = new MessageExchanger(socket, messageExchangers,logger);
                    messageExchangers.add(messageExchanger);
                    messageExchanger.start();
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}