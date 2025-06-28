import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket; // Socket for communication with the client
    private Set<PrintWriter> clientWriters; // Shared set of all client output writers
    private PrintWriter out; // Writer to send data to this client
    private BufferedReader in; // Reader to receive data from this client

    // Constructor to initialize socket and client writer list
    public ClientHandler(Socket socket, Set<PrintWriter> clientWriters) {
        this.socket = socket;
        this.clientWriters = clientWriters;
    }

    public void run() {
        try {
            // Set up input and output streams for this client
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Add this client's output stream to the shared set
            clientWriters.add(out);

            String message;

            // Continuously read messages from this client
            while ((message = in.readLine()) != null) {
                // Broadcast the message to all connected clients
                for (PrintWriter writer : clientWriters) {
                    writer.println("Client " + socket.getPort() + ": " + message);
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                // Clean up: close socket and remove writer from shared set
                socket.close();
                clientWriters.remove(out);
            } catch (IOException e) {
                System.out.println("Couldn't close socket");
            }
        }
    }
}
