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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class userController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private TextField tRegUsr;
    @FXML
    private TextField tRegNom;
    @FXML
    private TextField tRegPass;
    @FXML
    private TextField tRegAth;
    @FXML
    private TextField tEdtUsr;
    @FXML
    private TextField tEdtNom;
    @FXML
    private TextField tEdtPass;
    @FXML
    private TextField tEdtAth;
    @FXML
    private Label lRegLab;
    @FXML
    private Label lEdtLab;
    @FXML
    private TableView<lProducto> proTable;
    @FXML
    private TableColumn colNom;
    @FXML
    private TableColumn colUsr;
    @FXML
    private TableColumn colAth;

    private final ObservableList<lProducto> data =
            FXCollections.observableArrayList();


    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.screenMain);
        tRegUsr.setText("");
        tRegNom.setText("");
        tRegAth.setText("");
        tRegPass.setText("");
        lRegLab.setText("");

        tEdtUsr.setText("");
        tEdtNom.setText("");
        tEdtAth.setText("");
        tEdtPass.setText("");
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

    }
    @FXML
    private void bBuscar(ActionEvent event){

    }
    @FXML
    private void bEditar(ActionEvent event) {

    }
    @FXML
    private void bRegistrar(ActionEvent event){

    }

}
