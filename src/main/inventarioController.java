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
import javafx.scene.control.Label;
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
        lRegLab.setText("");
        lEdtLab.setText("");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private TextField tRegCod;
    @FXML
    private TextField tRegNom;
    @FXML
    private TextField tRegPre;
    @FXML
    private TextField tRegCan;
    @FXML
    private TextField tEdtCod;
    @FXML
    private TextField tEdtNom;
    @FXML
    private TextField tEdtPre;
    @FXML
    private TextField tEdtCan;
    @FXML
    private Label lRegLab;
    @FXML
    private Label lEdtLab;


    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.screenMain);
        tRegCod.setText("");
        tRegNom.setText("");
        tRegCan.setText("");
        tRegPre.setText("");
        lRegLab.setText("");

        tEdtCod.setText("");
        tEdtNom.setText("");
        tEdtCan.setText("");
        tEdtPre.setText("");
        lEdtLab.setText("");
        System.out.println("Go to Caja Screen");
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);;
        System.out.println("Go to Login Screen");
    }
    @FXML
    private void bBorrar(ActionEvent event){
        if(DbCapos.existeProducto(tEdtCod.getText())){
            DbCapos.deleteProducto(tEdtCod.getText());
            tEdtCod.setText("");
            tEdtNom.setText("");
            tEdtCan.setText("");
            tEdtPre.setText("");
        }
    }
    @FXML
    private void bBuscar(ActionEvent event){
        if(!tEdtCod.getText().isEmpty() && DbCapos.existeProducto(tEdtCod.getText())) {
            String[] valores = DbCapos.selectProduct(tEdtCod.getText());
            tEdtNom.setText(valores[0]);//Imprime el nombre en el textbox
            tEdtPre.setText(valores[1]);//Imprime el precio en el textbox
            tEdtCan.setText(valores[3]);//Imprime la cantidad en el textbox
        }  else {
            lEdtLab.setText("No Existe o Error");
        }
    }
    @FXML
    private void bEditar(ActionEvent event){
    }
    @FXML
    private void bRegistrar(ActionEvent event){
        tRegCod.setText(tRegCod.getText().trim());
        tRegNom.setText(tRegNom.getText().trim());
        tRegCan.setText(tRegCan.getText().trim());
        tRegPre.setText(tRegPre.getText().trim());
        if(DbCapos.existeProducto(tRegCod.getText())==false && !tRegCod.getText().equals("Ya Existe!") && !tRegCod.getText().equals("")){
            try {
                DbCapos.insertProducto(tRegCod.getText(), tRegNom.getText(), Float.parseFloat(tRegPre.getText()), Integer.parseInt(tRegCan.getText()));
                tRegPre.setStyle("-fx-background-color: white;");
                tRegCan.setStyle("-fx-background-color: white;");
                tRegCod.setText("");
                tRegNom.setText("");
                tRegCan.setText("");
                tRegPre.setText("");
                lRegLab.setText("");
            } catch (Exception e) {
                System.out.println("Error, solo se pueden numeros.");
                tRegPre.setStyle("-fx-background-color: red;");
                tRegCan.setStyle("-fx-background-color: red;");

            }
        } else {
            lRegLab.setText("Ya Existe o Error");
        }
    }
}
