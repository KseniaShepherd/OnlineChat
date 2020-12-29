package server;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MessageExchangerTest {

    @Test
    public void test() throws IOException {
        List<MessageExchanger> messageExchangers = new ArrayList<>();
        Socket socket = Mockito.mock(Socket.class);
        Logger logger = Mockito.mock(Logger.class);

        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("Ksusha\nhi\nexit".getBytes()));
        Mockito.when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        MessageExchanger messageExchanger = new MessageExchanger(socket, messageExchangers, logger);
        messageExchangers.add(messageExchanger);
        MessageExchanger messageExchangerSpy = Mockito.spy(messageExchanger);
        messageExchangerSpy.run();

        Mockito.verify(messageExchangerSpy, Mockito.times(1)).send("hi");

    }
}