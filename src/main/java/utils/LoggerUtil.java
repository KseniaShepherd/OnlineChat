package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

    public static Logger createLogger(String loggerName, String path) {
        Logger logger = Logger.getLogger(loggerName);
        try {
            FileHandler fh = new FileHandler(path,true);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
