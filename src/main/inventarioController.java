/*
 * 2014
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * 2014
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * 2014
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main;

import database.DbCapos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        colNombre.setCellValueFactory( new PropertyValueFactory<lProducto, String>("nombre"));
        colPrecio.setCellValueFactory( new PropertyValueFactory<lProducto, Float>("precio"));
        colCodigo.setCellValueFactory( new PropertyValueFactory<lProducto, String>("codigo"));
        colCantidad.setCellValueFactory( new PropertyValueFactory<lProducto, String>("cantidad"));
        lRegLab.setText("");
        lEdtLab.setText("");
        clear();
    }
    public void clear(){
        data.removeAll(data);
        int[] num = DbCapos.numProductos();
        for (int x=0; x<num.length; x++) {
            String[] valores = DbCapos.selectProduct(num[x]);
            //System.out.println(valores[0]+valores[1]+valores[2]+valores[3]);
            data.add(new lProducto(valores[0], valores[2], valores[1], valores[3]));
        }
        proTable.setItems(data);
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
    private TableView<lProducto> proTable;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TableColumn colCantidad;

    private final ObservableList<lProducto> data =
            FXCollections.observableArrayList();


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
        if(!DbCapos.existeProducto(tEdtCod.getText())==false && !tEdtCod.getText().equals("Ya Existe!") && !tEdtCod.getText().equals("")){
            DbCapos.deleteProducto(tEdtCod.getText());
            tEdtCod.setText("");
            tEdtNom.setText("");
            tEdtCan.setText("");
            tEdtPre.setText("");
            lEdtLab.setText("");
            clear();
        } else {
            lEdtLab.setText("No Existe o Error");
        }
    }
    @FXML
    private void bBuscar(ActionEvent event){
        clear();
        tEdtCod.setText(tEdtCod.getText().trim());
        if(!DbCapos.existeProducto(tEdtCod.getText())==false && !tEdtCod.getText().equals("Ya Existe!") && !tEdtCod.getText().equals("")){
            String[] valores = DbCapos.selectProduct(tEdtCod.getText());
            tEdtNom.setText(valores[0]);//Imprime el nombre en el textbox
            tEdtPre.setText(valores[2]);//Imprime el precio en el textbox
            tEdtCan.setText(valores[3]);//Imprime la cantidad en el textbox
        }  else {
            lEdtLab.setText("No Existe o Error");
        }
    }
    @FXML
    private void bEditar(ActionEvent event){
        tEdtCod.setText(tEdtCod.getText().trim());
        tEdtNom.setText(tEdtNom.getText().trim());
        tEdtCan.setText(tEdtCan.getText().trim());
        tEdtPre.setText(tEdtPre.getText().trim());
        if(!DbCapos.existeProducto(tEdtCod.getText())==false && !tEdtCod.getText().equals("Ya Existe!") && !tEdtCod.getText().equals("")){
            try {
                DbCapos.updateProducto(tEdtCod.getText(), tEdtNom.getText(), Float.parseFloat(tEdtPre.getText()), Integer.parseInt(tEdtCan.getText()));
                lEdtLab.setText("");
                tEdtCan.setStyle("-fx-background-color: white;");
                tEdtPre.setStyle("-fx-background-color: white;");
                clear();

            } catch (Exception e) {
                System.out.println("Error, solo se pueden numeros.");
                tEdtPre.setStyle("-fx-background-color: red;");
                tEdtCan.setStyle("-fx-background-color: red;");
                lEdtLab.setText("Numeros Invalidos");
            }
        }
    }
    @FXML
    private void bRegistrar(ActionEvent event){
        tRegCod.setText(tRegCod.getText().trim());
        tRegNom.setText(tRegNom.getText().trim());
        tRegCan.setText(tRegCan.getText().trim());
        tRegPre.setText(tRegPre.getText().trim());
        try {
            if (tRegCod.getText().length()>10 || tRegNom.getText().length()>10){
                throw new Exception("Tamano de Caracteres");
            }
            if(DbCapos.existeProducto(tRegCod.getText())==false && !tRegCod.getText().equals("Ya Existe!") && !tRegCod.getText().equals("")){
                    DbCapos.insertProducto(tRegCod.getText(), tRegNom.getText(), Float.parseFloat(tRegPre.getText()), Integer.parseInt(tRegCan.getText()));
                    tRegPre.setStyle("-fx-background-color: white;");
                    tRegCan.setStyle("-fx-background-color: white;");
                    tRegCod.setText("");
                    tRegNom.setText("");
                    tRegCan.setText("");
                    tRegPre.setText("");
                    lRegLab.setText("");
                    clear();
            } else {
                lRegLab.setText("Ya Existe o Error");
            }
        } catch (NumberFormatException e){
            System.out.println("Error, solo se pueden numeros. Codigo y nombre solo puede hasta 10 caracteres.");
            lRegLab.setText("Error: Numberos Invalidos");
            tRegPre.setStyle("-fx-background-color: red;");
            tRegCan.setStyle("-fx-background-color: red;");
        } catch (Exception e) {
            System.out.println("Error, solo se pueden numeros. Codigo y nombre solo puede hasta 10 caracteres.");
            lRegLab.setText("Error: Codigo y/o Nombre Tamano de Caracteres Invalido");
        }
    }
}
