package program.connection;

import java.util.ArrayList;


public class ServerMain {
    public static ArrayList<String> users=new ArrayList<>();
    public static void main(String args[]) {

        int port = 6056;
        Server server = new Server(port);
        server.start();

    }

}
