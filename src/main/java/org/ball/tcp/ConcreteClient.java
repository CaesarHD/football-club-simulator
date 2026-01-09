package org.ball.tcp;

import java.io.*;
import java.net.Socket;

public class ConcreteClient {
    public static void main(String[] args) throws Exception {
        try (Socket s = new Socket("localhost", 5555);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("SERVER: " + in.readLine()); // WELCOME

            String line;
            while ((line = stdin.readLine()) != null) {
                out.println(line);
                System.out.println("SERVER: " + in.readLine());
            }
        }
    }
}
