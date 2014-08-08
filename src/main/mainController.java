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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class mainController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToCaja(ActionEvent event){
        myController.setScreen(Main.screenCaja);
        System.out.println("Go to Caja Screen");
    }

    @FXML
    private void goToReportes(ActionEvent event){
        //myController.setScreen(Main.screen3ID);
        System.out.println("Go to Reportes Screen");
    }
    @FXML
    private void goToInventario(ActionEvent event){
        //myController.setScreen(Main.screen3ID);
        System.out.println("Go to Inv Screen");
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);;
        System.out.println("Go to Login Screen");
    }
}