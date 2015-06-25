package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class aboutController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String buildV = "0.0.4";
        lbuild.setText("Build: v"+buildV);

        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("CAPOS GITHUB OPENED");
                openWebpage("https://github.com/antoniotorres/CAPOS");
            }
        });

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    Label lbuild;
    @FXML
    Hyperlink link;
    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.screenMain);
        System.out.println("Go to Login Screen");
    }
    public static void openWebpage(String url) {
        try {
            new ProcessBuilder("x-www-browser", url).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
