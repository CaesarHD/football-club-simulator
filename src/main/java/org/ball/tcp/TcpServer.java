package org.ball.tcp;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TcpServer {

    private final TcpRouter router;

    private final ExecutorService pool = Executors.newCachedThreadPool();
    private volatile boolean running = false;
    private ServerSocket serverSocket;

    public TcpServer(TcpRouter router) {
        this.router = router;
    }

    public void start(int port) {
        if (running) return;

        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("[TCP] Server started on port " + port);

            pool.submit(() -> acceptLoop());

        } catch (IOException e) {
            throw new RuntimeException("Cannot start TCP server", e);
        }
    }

    private void acceptLoop() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("[TCP] New client connected: " + socket.getRemoteSocketAddress());

                pool.submit(() -> handleClient(socket));

            } catch (IOException e) {
                if (running) System.out.println("[TCP] accept error: " + e.getMessage());
            }
        }
    }

    private void handleClient(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println("WELCOME");

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[TCP] MSG: " + line);

                String response = router.handle(line);   // AICI e legătura cu serviciile tale
                out.println(response);
            }

        } catch (IOException e) {
            System.out.println("[TCP] Client error: " + e.getMessage());
        } finally {
            System.out.println("[TCP] Client disconnected: " + socket.getRemoteSocketAddress());
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored) {}
        System.out.println("[TCP] Server stopped");
    }

    @PreDestroy
    public void shutdown() {
        stop();
        pool.shutdownNow();
    }
}
