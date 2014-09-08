/*
*CAPOS is a open source Point of Sale made on JAVA.
*Copyright (C) 2014  Jose Antonio Torres
*
*This file is part of CAPOS.
*
*CAPOS is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*CAPOS is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with CAPOS.  If not, see <http://www.gnu.org/licenses/>.
*/

package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
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
        // TODO
        //Esta parte del codigo muestra el logo de la empresa
        PropCapos prop = new PropCapos();
            Image image = new Image("file:"+prop.getLogo(), 256, 100, false, false);
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
        System.out.println("loginButton");
        if (DbCapos.selectLogin(username.getText(), password.getText())) {
            System.out.println("Username and Password Match");
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
