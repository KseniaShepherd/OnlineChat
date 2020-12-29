package server;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class MessageExchangerTest {

    @Test
    public void test() throws IOException, InterruptedException {
        List<MessageExchanger> messageExchangers = new CopyOnWriteArrayList<>();
        Socket socket = Mockito.mock(Socket.class);
        Logger logger = Mockito.mock(Logger.class);

        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("Ksusha\nhi\nexit".getBytes()));
        Mockito.when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        MessageExchanger messageExchanger = new MessageExchanger(socket, messageExchangers, logger);
        MessageExchanger messageExchangerSpy = Mockito.spy(messageExchanger);
        messageExchangers.add(messageExchangerSpy);
        messageExchangerSpy.start();
        messageExchangerSpy.join();

        Mockito.verify(messageExchangerSpy, Mockito.times(1)).send("hi");

    }
}