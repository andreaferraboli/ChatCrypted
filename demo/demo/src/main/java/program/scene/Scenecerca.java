package program.scene;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static program.scene.SecondWindow.listFiles;

public class Scenecerca extends Application {

public GridPane risultatoricerca=new GridPane();
private Stage stage=new Stage();
        public void start(Stage stage) throws IOException {
            //GridPane root = FXMLLoader.load(getClass().getResource("Sceneenterserver.fxml"));
            GridPane root=new GridPane();
            GridPane g1=new GridPane();
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
            });
            TextField t1=new TextField();
            t1.setPrefHeight(25.0);
            t1.setPrefWidth(209.0);
            t1.setAlignment(Pos.TOP_CENTER);
            Button cerca = new Button("trova");
            cerca.setId("cerca");
            cerca.setOnAction(e -> {
                if(esistericerca(t1.getText(),cerca)){
                try {
                    if(Cercachat(t1,cerca)){
                        Scenadopocerca prymarystage = new Scenadopocerca(risultatoricerca);
                        try {
                            prymarystage.start(stage);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }


            }});
            Label l1=new Label("stringa da cercare");
            l1.setAlignment(Pos.CENTER);
            l1.setPrefSize(366.0,25.0);
            l1.setFont(new Font("Arial",21));
            root.add(t1,1,1);
            root.add(l1,0,1);
            root.add(indietro,0,0);
            cerca.setAlignment(Pos.CENTER);
            root.add(cerca,2,1);
            Scene scene = new Scene(root,700,100);
            stage.setTitle("Cerca");
            scene.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());
            stage.centerOnScreen();
            stage.setScene(scene);
            }

    private boolean esistericerca(String parola,Button cerca) {
        Window owner = cerca.getScene().getWindow();
        if(parola.isEmpty()) {
            alert.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a word to search");
            return false;
        }
        return true;
    }

    public boolean Cercachat(TextField t1,Button cerca) throws FileNotFoundException {
        List<String> userchat = new ArrayList<String>();
        File folder = new File(MainMenu.pathname);
        listFiles(folder, userchat);
        int trovato=0;
        int i=0;
        for (int n = 0; n < userchat.size(); n++) {
            String path = MainMenu.pathname+"\\";
            String namefile = userchat.get(n);

            String chatusername = namefile.replaceFirst(".txt", "");
            chatusername=chatusername.replaceFirst("group_","");
                FileReader f1 = new FileReader(path + namefile);
                Scanner reader = new Scanner(f1);
                String testoButton;
                while (reader.hasNext() ) {
                    i++;
                    testoButton = reader.nextLine();
                    if(testoButton.contains(t1.getText())){
                        String parola=t1.getText();
                        String preparola=testoButton.substring(0,testoButton.indexOf(parola));
                        String dopoparola=testoButton.substring(testoButton.indexOf(parola)+parola.length());
                        trovato++;
                        Text chat=new Text("chat: "+chatusername);
                        chat.setFill(Color.CORAL);
                        chat.setFont(new Font("Calibri",18));
                        TextFlow textFlowPane = new TextFlow();
                        Text frase = new Text(preparola);
                        frase.setFill(Color.BLACK);
                        Text redText = new Text(parola);
                        redText.setFill(Color.RED);
                        Text frase2 = new Text(dopoparola);
                        frase2.setFill(Color.BLACK);
                        textFlowPane.getChildren().addAll(frase, redText,frase2);

                        risultatoricerca.add(chat,0,i);
                        risultatoricerca.add(textFlowPane,0,++i);
                        i++;
                    }

                }

        }
        if(trovato==0){
            Window owner = cerca.getScene().getWindow();
            alert.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "non esiste la parola cercata nelle tue chat");
            return false;
        }
        return true;
    }
}

