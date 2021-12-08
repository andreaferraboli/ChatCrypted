package program.scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Scenadopocerca extends Application {
    private GridPane root;

    public Scenadopocerca(GridPane root) {
        this.root = root;
    }

        public void start(Stage stage) throws IOException {
            GridPane g1=new GridPane();

            Button indietro = new Button("<-");
            indietro.setId("indietro");
            indietro.setOnAction(e -> {
                Scenecerca prymarystage = new Scenecerca();
                try {
                    prymarystage.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            g1.add(indietro,0,0);
            g1.add(root,0,1);
            g1.setMinSize(300,200);

            Scene scene = new Scene(g1);
            stage.setTitle("risultati ricerca");
            scene.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());
            stage.centerOnScreen();
            stage.setScene(scene);
            }
        }

