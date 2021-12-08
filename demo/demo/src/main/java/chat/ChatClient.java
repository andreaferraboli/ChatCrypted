package chat;

import com.example.demo.HelloController;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Thread {

    public static int port;
    public static String address;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public ChatClient(String serverIp, int serverPort) {
        port = serverPort;
        address = serverIp;

    }

    public void run() {

        try {

            Socket sock = new Socket(address, port);

            BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), true);
            System.out.println("Connection established");

            Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br);
            chat_client_writer.start();

            String s;
            while ((s = sock_br.readLine()) != null) {
                Platform.runLater(
                        () -> {
//                            HelloController.chat.getItems().add(new Label("\rserver: " + AES.decrypt(s, AES.sK)));
                            HelloController.addElementToChat("ciao zio cane");
                        }
                );

                System.out.println("\rserver: " + AES.decrypt(s, AES.sK));
                System.out.print("> ");
            }
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}