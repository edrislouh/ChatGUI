package main;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

	  private static final int PORT = 2332;
	  private static Map<String, PrintWriter> clients = new HashMap<>();

	  public static void main(String[] args) {
	    System.out.println("The chat server is running...");
	    try (ServerSocket listener = new ServerSocket(PORT)) {
	      while (true) {
	        Socket socket = listener.accept();
	        System.out.println("New connection: " + socket);
	        ClientHandler client = new ClientHandler(socket);
	        client.start();
	      }
	    } catch (IOException e) {
	      System.out.println("Server exception: " + e.getMessage());
	    }
	  }

	  private static class ClientHandler extends Thread {
	    private Socket socket;
	    private BufferedReader reader;
	    private PrintWriter writer;
	    private String username;

	    public ClientHandler(Socket socket) {
	      this.socket = socket;
	    }

	    public void run() {
	      try {
	        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        writer = new PrintWriter(socket.getOutputStream(), true);

	        // Read and store the username
	        username = reader.readLine();
	        System.out.println(username + " joined the chat");

	        // Store the PrintWriter in the clients map
	        clients.put(username, writer);

	        String message;
	        while ((message = reader.readLine()) != null) {
	          System.out.println(username + ": " + message);
	          // Broadcast the message to all connected clients
	          broadcastMessage(username + ": " + message);
	        }
	      } catch (IOException e) {
	        System.out.println("Error handling client: " + e.getMessage());
	      } finally {
	        if (writer != null) {
	          clients.remove(username);
	        }
	        try {
	          socket.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }

	    private void broadcastMessage(String message) {
	        for (PrintWriter clientWriter : clients.values()) {
	            clientWriter.println(message);
	            clientWriter.flush(); // Ensure the message is immediately sent
	        }
	    }
	  }
	}
