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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.ResourceBundle;
import database.DbCapos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
        if (DbCapos.selectLogin(username.getText(), hashPassword(password.getText()))) {
            //Este codigo va a cambiar la pantalla a screenMain
            myController.setScreen(Main.screenMain);
            lError.setText("");
            password.setText("");

        }else {
            System.out.println("Error: Maybe Username or Password wrong");
            lError.setText("Error: Usuario o Contrasena Mal");
        }
    }
    //Encrypts User Password
    private String hashPassword(String pwd){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pwd.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException ex) {

        } catch (NoSuchAlgorithmException ex) {

        }
        return digest;
    }
}
