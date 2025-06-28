
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234; // Port number for server to listen on

    // Synchronized set to store all connected client output streams
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());




    public static void main(String[] args) throws IOException {
        try (// Create a server socket to listen for connections
        ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            // Keep accepting new client connections
            while (true) {
                // Accept the incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket); /* till now thread accepts only one client, hence
                now we will create a  new thread class (ClientHandler).java so it can handle multiple clients. */


                // Create a new thread to handle communication with this client
                new ClientHandler(clientSocket, clientWriters).start();
            }
        }
    }
}


/*1. Start the server-

javac ChatServer.java ClientHandler.java
java ChatServer


2.Open multiple terminals and run the client

javac ChatClient.java
java ChatClient


 */