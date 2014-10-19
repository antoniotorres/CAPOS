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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import database.DbCapos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import properties.PropCapos;

public class mainController extends ControlledScreen implements Initializable {

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
        Image image = new Image("file:"+propertiesPath+"/"+prop.getStore_logo(), 128, 50, false, false);
        imgLogoCom.setImage(image);

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private ImageView imgLogoCom;
    @FXML
    private void goToCaja(ActionEvent event){
        myController.setScreen(Main.screenCaja);
        System.out.println("Go to Caja Screen");
    }

    @FXML
    private void goToReportes(ActionEvent event){
        myController.setScreen(Main.screenReportes);
        System.out.println("Go to Reportes Screen");
    }
    @FXML
    private void goToInventario(ActionEvent event){
        myController.setScreen(Main.screenInventario);
        System.out.println("Go to Inv Screen");
    }
    @FXML
    private void goToPromociones(ActionEvent event){
        myController.setScreen(Main.screenPromociones);;
        System.out.println("Go to Promociones Screen");
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);;
        System.out.println("Go to Login Screen");
    }
}
