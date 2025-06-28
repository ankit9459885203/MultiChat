import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        // Connect to the server running on localhost at port 1234
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to chat server.");

        // Reader to get messages from the server
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Writer to send messages to the server
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Reader to read user input from the terminal
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Thread to continuously read and display messages from server
        new Thread(() -> {
            String msgFromServer;
            try {
                while ((msgFromServer = input.readLine()) != null) {
                    System.out.println(msgFromServer); // Print incoming message
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();

        // Main thread to send user messages to the server
        String userMsg;
        while ((userMsg = userInput.readLine()) != null) {
            output.println(userMsg); // Send message to server
        }

        // Close the socket if client input loop ends
        socket.close();
    }
}
/*


1. Start the server-

javac ChatServer.java ClientHandler.java
java ChatServer


2.Open multiple terminals and run the client

javac ChatClient.java
java ChatClient
















 * 
 * 
 * 
 * 
 * 
 * 
 */