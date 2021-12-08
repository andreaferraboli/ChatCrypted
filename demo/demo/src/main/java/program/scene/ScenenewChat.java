package program.scene;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenenewChat extends Application {

    public void start(Stage stage) throws IOException {

            GridPane root=new GridPane();
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
            root.add(indietro,0,0);
            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.setPrefWidth(200.0);
            //creo la dimensione delle colonne
            for (int i = 0; i < 3; i++) {
                ColumnConstraints column;
                if(i==0)
                {
                    column = new ColumnConstraints(50);
                }
                else{
                    column = new ColumnConstraints(150);
                }
                root.getColumnConstraints().add(column);

            }
            for (int i = 0; i < MainMenu.utentiOnline.size(); i++) {
                choiceBox.getItems().add(MainMenu.utentiOnline.get(i));
            }

            root.add(choiceBox,1,2);
            indietro.setAlignment(Pos.TOP_LEFT);
            Button invio = new Button("crea");

            invio.setId("invio");
            invio.setOnAction(e -> {
                String value = (String) choiceBox.getValue();
                MainMenu.NewChat(value);
            });

            root.add(invio,2,2);
            Scene scene = new Scene(root,300,100);
            stage.setTitle("New Chat");
            scene.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());
            stage.centerOnScreen();
            stage.setScene(scene);
    }
}