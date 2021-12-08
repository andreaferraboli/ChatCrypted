package program.scene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import program.connection.ChatClient;

import java.io.*;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

public class SecondWindow extends Application {
    public static String nomechat="";
    public static GridPane gridchat = new GridPane();
    public static String refresh;
    public static int gridpanei=0;
    private static ScrollPane s1;

    // This method, when called, will receive the original primary stage
// on which a new scene will then be attached
    public SecondWindow(String nomechat) {

        this.nomechat = nomechat;
    }
    public static void showmessage(String gruppo,String sender, String msg) throws FileNotFoundException {
        List<String> userchat = new ArrayList<String>();
        final File folder = new File(MainMenu.pathname);
        listFiles(folder, userchat);
        for (int n = 0; n < userchat.size(); n++) {
            String namefile = userchat.get(n);
            if (namefile.startsWith(gruppo) ) {
                String path = MainMenu.pathname + "\\";
                FileOutputStream file;
                if (namefile.contains("group_")) {
                    try {
                        file = new FileOutputStream(path + gruppo + ".txt", true);
                        PrintWriter p1 = new PrintWriter(file);
                        p1.print("\n" + sender + ":" + msg);
                        p1.close();
                        /*
                        if(nomechat.equals((refresh))){
                            SecondWindow s2=new SecondWindow(nomechat);
                            s2.start(MainProgram.commonScene);
                        }

                         */
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Runnable updater = new Runnable() {

                                    @Override
                                    public void run() {
                                        if(nomechat.equals(refresh)) {
                                            gridpanei++;
                                            Label mit = new Label(sender);
                                            mit.setId("ricevutogruppo");
                                            gridchat.add(mit, 0, gridpanei);
                                            gridpanei++;
                                            Button mex = new Button(msg);
                                            mex.setId("ricevuto1");
                                            mex.setMinHeight(50.0);
                                            mex.setMinWidth(200.0);
                                            mex.setMaxWidth(200.0);
                                            gridchat.add(mex, 0, gridpanei);
                                            s1.layout();
                                            s1.setVvalue(1);
                                        }
                                    }
                                };
                                    Platform.runLater(updater);

                            }

                        });
                        // don't let thread prevent JVM shutdown
                        thread.setDaemon(true);
                        thread.start();


                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
                n=userchat.size();
            }
            else{
                if(namefile.equals(sender+".txt") && !gruppo.contains("group_")){
                    n=userchat.size();
                    String path = MainMenu.pathname + "\\";
                    FileOutputStream file;
                    try{
                    file = new FileOutputStream(path + sender + ".txt", true);
                    PrintWriter p1 = new PrintWriter(file);
                    p1.print("\n" + sender + ":" + msg);
                    p1.close();
                    Platform.runLater(()->{
                        gridpanei++;
                        Button mex=new Button(msg);
                        mex.setId("ricevuto");
                        mex.setMinHeight(50.0);
                        mex.setMinWidth(200.0);
                        mex.setMaxWidth(200.0);
                        if(nomechat.equals(sender)){
                            gridchat.add(mex,0,gridpanei);
                            s1.layout();
                            s1.setVvalue(1);
                        }

                    });

                } catch (Exception e) {
                    System.err.println(e);
                }

                }
            }
        }
    }
    public void start(Stage stage) throws FileNotFoundException {
        if (nomechat.equals("")) {
            MainMenu prymarystage = new MainMenu();
            try {
                prymarystage.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            GridPane g1=new GridPane();
            GridPane g2=new GridPane();
            buildscene2(nomechat);
            Button indietro = new Button("<-");
            indietro.setId("indietro");
            indietro.setOnAction(e -> {
                //s1.interrupt();
                MainMenu prymarystage = new MainMenu();
                try {
                    prymarystage.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                gridpanei = 0;
            });
            g1.add(indietro,0,0);

            HBox hbox = new HBox();
            TextField testo = new TextField();
            testo.setPromptText("invia un testo:");
            Button invio = new Button("invio");
            invio.setId("invio");
                invio.setOnAction(e -> {

                try {
                    if(!testo.getText().equals(""))
                        HAndlesubmitbutton(testo.getText(),nomechat);
                } catch (IOException socketException) {
                    socketException.printStackTrace();
                }
                SecondWindow s2 = new SecondWindow(nomechat);
                try {
                    s2.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                invio.setText("");
            });
            testo.setPrefWidth(550.0);
            g2.add(testo,0,0);
            invio.setPrefWidth(50.0);
            g2.add(invio,1,0);
             s1=new ScrollPane();
             gridchat.setAlignment(Pos.TOP_CENTER);
             gridchat.setPrefSize(750,300);
            s1.setContent(gridchat);
            s1.setMaxSize(600,300);
            s1.setPrefSize(590,300);
            s1.hbarPolicyProperty (). setValue (ScrollPane.ScrollBarPolicy.NEVER);
            s1.setVvalue(1);
            g1.add(s1,0,2);
            g1.add(g2,0,3);
            Scene scene = new Scene(g1);
            scene.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(nomechat);
            stage.show();
        }
    }

    public void HAndlesubmitbutton(String messaggio,String nomechat) throws IOException {


            try {
            String str1 = messaggio;
            //manda messaggio online
            String path= MainMenu.pathname+"\\";


            FileOutputStream file = new FileOutputStream(path+nomechat+".txt",true);

            PrintWriter p1= new PrintWriter(file);
            p1.print("\nio:"+messaggio);
            p1.close();

            } catch (Exception e) {
                System.err.println(e);
            }
            if(nomechat.contains("group")){
                nomechat=nomechat.replaceFirst("group_","");
                ChatClient.sendmessageGroup(messaggio,nomechat);

            }
            else{
                ChatClient.sendmessage(messaggio, nomechat);
            }




    }


    public void buildscene2(String chat) throws FileNotFoundException {
        gridchat=new GridPane();

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridchat.getColumnConstraints().addAll(column1, column2);


        List<String> userchat = new ArrayList<String>();
        final File folder = new File(MainMenu.pathname);
        listFiles(folder, userchat);
        for (int n = 0; n < userchat.size(); n++) {
            String path = MainMenu.pathname+"\\";
            String namefile = userchat.get(n);
            if (namefile.contains(chat)) {

                FileReader f1 = new FileReader(path + namefile);
                Scanner reader = new Scanner(f1);
                int j;
                String testoButton;


                while (reader.hasNext() ) {
                    gridpanei++;
                    testoButton = reader.nextLine();
                    testoButton=testoButton.trim();
                    if(testoButton.length()>0) {

                        if(namefile.contains("group_")){

                            Button messaggio=new Button();
                            if(testoButton.contains("io:")){
                                j = 1;
                                testoButton = testoButton.replaceAll("io:", "");
                                messaggio.setId("inviato");
                                messaggio.setAlignment(Pos.CENTER_RIGHT);
                            }
                            else{
                                j = 0;


                                Label mittente=new Label();
                                mittente.setId("ricevutogruppo");
                                messaggio.setId("ricevuto1");

                                mittente.setText(testoButton.substring(0,testoButton.indexOf(":")));
                                mittente.setAlignment(Pos.BOTTOM_LEFT);
                                gridchat.add(mittente,j,gridpanei);
                                gridpanei++;
                                testoButton=testoButton.substring(testoButton.indexOf(":")+1);

                            }
                            messaggio.setText(testoButton);
                            messaggio.setMinHeight(50.0);
                            messaggio.setMinWidth(200.0);
                            messaggio.setMaxWidth(200.0);
                            messaggio.setAlignment(Pos.CENTER);
                            gridchat.add(messaggio, j, gridpanei);
                        }
                        else{
                            gridpanei++;
                            Button temp = new Button(testoButton);
                            String chatusername = namefile.replaceFirst(".txt", "");
                            if (testoButton.contains("io:")) {
                                j = 1;
                                testoButton = testoButton.replaceAll("io:", "");
                                temp.setId("inviato");
                            } else {
                                j = 0;
                                testoButton = testoButton.replaceAll(chatusername + ":", "");
                                temp.setId("ricevuto");
                            }
                            temp.setText(testoButton);
                            temp.setTextAlignment(TextAlignment.RIGHT);
                            temp.setMinHeight(50.0);
                            temp.setMinWidth(200.0);
                            temp.setMaxHeight(200.0);
                            gridchat.add(temp, j, gridpanei);
                        }

                    }
                }
            }
        }

    }


    public static void listFiles(File folder, List<String> elenconomi) {
        for (final File fileEntry : folder.listFiles()) {
            elenconomi.add(fileEntry.getName());
            //System.out.println(fileEntry.getName());
        }
    }




    public static void main(String[] args) {
        launch(args);
    }

}
