package proxy;

import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Created by prabhjotkaur on 08/04/2017.
 */

public class ProxyThread extends Thread {
    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;
    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        try {

            BufferedReader inputStreamReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            DataOutputStream dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());

            String inputLine;
            int counter = 0;
            String urlToCall = "";

            //get request from client
            while ((inputLine = inputStreamReader.readLine()) != null) {

                //parse the first line of the request to find the url
                if (counter == 0) {
                    //get url to call
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[0];
                    System.out.println("Request for : " + urlToCall);
                }

                counter++;
            }


            BufferedReader bufferedReader = null;
            try {

                URL url = new URL(urlToCall);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(false);

                //System.dataOutputStream.println("content length: " + urlConnection.getContentLength());

                // Get the response
                InputStream inputStream = null;
                if (urlConnection.getContentLength() > 0) {
                    try {
                        inputStream = urlConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        //Cache the response inputStreamReader a LRUCache//
                        while ((inputLine = bufferedReader.readLine()) != null) {
                            //store response line by line inputStreamReader cache//
                            LRUCache.set(urlToCall,inputLine);
                        }

                    } catch (IOException ioe) {
                        System.out.println(
                                "IO Exception" + ioe);
                    }
                }

                //send response to client
                byte bufferArray[] = new byte[ BUFFER_SIZE ];
                int index = inputStream.read( bufferArray, 0, BUFFER_SIZE );
                while ( index != -1 )
                {
                    dataOutputStream.write( bufferArray, 0, index );
                    index = inputStream.read( bufferArray, 0, BUFFER_SIZE );
                }
                dataOutputStream.flush();

            } catch (Exception e) {
                System.err.println("Exception: " + e);
                dataOutputStream.writeBytes("");
            }

            //close dataOutputStream all resources
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
