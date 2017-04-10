package proxy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by prabhjotkaur on 08/04/2017.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",10000);

        try{
            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());
            String urlToCall = "https://www.google.co.uk/search?q=soma+analytics";
            out.writeBytes(urlToCall);
        }catch (IOException ioe) {
            System.out.println(
                    "IO Exception" + ioe);
        }


    }
}
