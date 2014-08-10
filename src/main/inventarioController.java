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

import database.DbCapos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class inventarioController extends ControlledScreen implements Initializable {

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
    private TextField tViewCod;
    @FXML
    private TextField tViewNom;
    @FXML
    private TextField tViewPre;
    @FXML
    private TextField tViewCan;
    @FXML
    private TextField tAgreCod;
    @FXML
    private TextField tAgreNom;
    @FXML
    private TextField tAgrePre;
    @FXML
    private TextField tAgreCan;
    @FXML
    private TextField tBorrar;

    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.screenMain);
        System.out.println("Go to Caja Screen");
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);;
        System.out.println("Go to Login Screen");
    }
    @FXML
    private void bBorrar(ActionEvent event){
        if(DbCapos.existeProducto(tBorrar.getText())){
            DbCapos.deleteProducto(tBorrar.getText());
        }
    }
    @FXML
    private void bAgreguar(ActionEvent event){
        if(DbCapos.existeProducto(tAgreCod.getText())==false){
            DbCapos.insertProducto(tAgreCod.getText(), tAgreNom.getText(), Float.parseFloat(tAgrePre.getText()), Integer.parseInt(tAgreCan.getText()));
        }
    }
    @FXML
    private void bView(ActionEvent event){
        if(DbCapos.existeProducto(tViewCod.getText())){
            String[] valores = DbCapos.selectProduct(tViewCod.getText());
            tViewNom.setText(valores[0]);
            tViewPre.setText(valores[1]);
            tViewCan.setText(valores[3]);
        }
    }
}
