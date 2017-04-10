package proxy;
import java.net.*;
import java.io.*;

/**
 * Created by prabhjotkaur on 08/04/2017.
 */

public class ProxyServer {
    public static void main(String[] args) throws IOException {
        boolean listening = true;
        int port = 10000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Started on port:" + port);
        while (listening) {
            new proxy.ProxyThread(serverSocket.accept()).start();
        }
        serverSocket.close();
    }
}
