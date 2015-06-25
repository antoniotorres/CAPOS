package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import database.DbCapos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import properties.PropCapos;

public class loginController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Esta parte del codigo muestra el logo de la empresa
        PropCapos prop = new PropCapos();
        File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
            Image image = new Image("file:"+propertiesPath+"/"+prop.getStore_logo(), 256, 100, false, false);
            imgLogoCom.setImage(image);
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private ImageView imgLogoCom;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label lError;

    @FXML
    private void loginButton(ActionEvent event){

        //myController.setScreen(Main.screen2ID);
        if (DbCapos.login(username.getText(), password.getText())) {
            //Este codigo va a cambiar la pantalla a screenMain
            myController.setScreen(Main.screenMain);
            lError.setText("");
            password.setText("");

        }else {
            System.out.println("Error: Maybe Username or Password wrong");
            lError.setText("Error: Usuario o Contrasena Mal");
        }
    }
}
