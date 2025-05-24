import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Connected to the chat server.");

            // Thread to read messages from the server
            Thread readerThread = new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = input.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });

            readerThread.start();

            // Main thread sends messages
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                output.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
