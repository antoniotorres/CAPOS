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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

import database.DbCapos;

public class cajaController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lSubtotal.setText("$0.00");
        lTax.setText("$0.00");
        lTotal.setText("$0.00");
        colNombre.setCellValueFactory( new PropertyValueFactory<Lista, String>("nombre"));
        colPrecio.setCellValueFactory( new PropertyValueFactory<Lista, Float>("precio"));
        colCodigo.setCellValueFactory( new PropertyValueFactory<Lista, String>("codigo"));
    }
    @FXML
    private Label lSubtotal;
    @FXML
    private Label lTax;
    @FXML
    private Label lTotal;
    @FXML
    private TableView<Lista> cajaTable;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TextField tSearch;

    private float vSubtotal = 0.00f;
    private float vTax = 0.00f;
    private float vTotal = 0.00f;

    private final ObservableList<Lista> data =
            FXCollections.observableArrayList();



    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);
        System.out.println("Go to Login Screen");
    }
    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.screenMain);
        System.out.println("Go to Main Screen");
    }
    @FXML
    private void goToCancel(ActionEvent event){
        myController.setScreen(Main.screenCaja);
        data.removeAll(data);
        vSubtotal = 0.00f;
        vTax = 0.00f;
        vTotal = 0.00f;
        lSubtotal.setText("$0.00");
        lTax.setText("$0.00");
        lTotal.setText("$0.00");
        tSearch.setText("");
        System.out.println("Go to Caja Screen");
    }
    //Esta funcion al momento de buscar update la tabla y la informacion de venta
    @FXML
    private void addProduct(ActionEvent event){
        String[] valores = DbCapos.selectProduct(tSearch.getText());
        data.add(new Lista (valores[0], valores[2], valores[1]));
        addVenta(Float.parseFloat(valores[1]));
        cajaTable.setItems(data);
    }


    //Este metodo  agregua los precios al subtotal, Impuesto y Total
    public void addVenta(float var){
        this.vSubtotal = this.vSubtotal + var;
        this.lSubtotal.setText("$ "+String.valueOf(this.vSubtotal));
        this.vTotal = this.vSubtotal + this.vTax;
        this.lTotal.setText("$ "+String.valueOf(this.vTotal));
    }
}
