package program.connection;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

public class ServerWorker extends Thread{

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    private HashSet<String> topicSet = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            if(e.getMessage().equalsIgnoreCase("Connection reset")){
                System.out.println("Client disconnected... Waiting for another connection");
            }else{
                e.printStackTrace();
            }
        }
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ( (line = reader.readLine()) != null ) {
            String [] tokens = StringUtils.split(line);

            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(cmd)) {
                    handleLogoff();
                    break;
                }
                else if ("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                }
                else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
                }
                else if ("leavet".equalsIgnoreCase(cmd)) {
                    handleLeavet(tokens);
                }
                else if ("msg".equalsIgnoreCase(cmd)) {
                    String tokensMsg [] = StringUtils.split(line, null, 3);
                    handleMessage(tokensMsg);
                }
                else {
                    String msg = "unknown " + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }
            }
        }
    }

    private void handleLeavet(String[] tokens) {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.remove(topic);
        }
    }

    public boolean isMemberOfTopic(String topic) {
        return topicSet.contains(topic);
    }

    private void handleJoin(String[] tokens) throws IOException {
        if (tokens.length > 1) {

            String topic = tokens[1];
            topicSet.add(topic);
            String temp = "aggiunto al gruppo:" + topic + "\n";
            outputStream.write(temp.getBytes());

        }
    }

    private void handleMessage(String[] tokens) throws IOException {
        String sendTo = tokens[1];
        String body = tokens[2];

        boolean isTopic = sendTo.charAt(0) == '#';

        List<ServerWorker> workerList = server.getWorkerList();
        for (ServerWorker worker : workerList) {
            if (isTopic) {
                if(worker.isMemberOfTopic(sendTo)) {
                    String outMsg = sendTo + ": " + login + ": " + body + "\n";
                    worker.send(outMsg);
                }

            }
            else {
                if (worker.getLogin().equals(sendTo)) {
                    String outMsg = "msg " + sendTo + " " + body + "\n";
                    worker.send(outMsg);
                }
            }
        }
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();


        //send other online users current user s status
        String onlineMsg = "offline " + login + "\n";
        for (ServerWorker worker : workerList) {
                worker.send(onlineMsg);
        }
        clientSocket.close();
    }



    public String getLogin() {
        return login;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {

            String login = tokens[1];
            String password = tokens[2];
            //(login.equals("guest") && password.equals("guest")) || (login.equals("yeg") && password.equals("yeg"))
            if (true) {
                String msg = "login done\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in successfully: " + login);

                List<ServerWorker> workerList = server.getWorkerList();
                //send to a current user all other online logins
                for (ServerWorker worker : workerList) {

                        if (worker.getLogin() != null) {
                            if(!login.equals(worker.getLogin())) {
                                String onlineMsg2 = "online " + worker.getLogin() + "\n";
                                send(onlineMsg2);
                            }
                        }
                }
                //send other online users current user s status
                String onlineMsg = "online " + login + "\n";
                for (ServerWorker worker : workerList) {
                    if(!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            }
            else {
                String msg = "Login error, retry.\n";
                outputStream.write(msg.getBytes());
                System.out.println("Login error,retry.\n");
            }
        }
    }

    private void send(String onlineMsg) throws IOException {
        if (login != null) {
            outputStream.write(onlineMsg.getBytes());
        }
    }

}