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
import utilities.reports;

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
        reports.log("Go to Caja Screen");
    }

    @FXML
    private void goToReportes(ActionEvent event){
        myController.setScreen(Main.screenReportes);
        reports.log("Go to Reports Screen");
    }
    @FXML
    private void goToInventario(ActionEvent event){
        myController.setScreen(Main.screenInventario);
        reports.log("Go to Inventory Screen");
    }
    @FXML
    private void goToSettings(ActionEvent event){
        myController.setScreen(Main.screenSettings);
        reports.log("Go to Settings Screen");
    }
    @FXML
    private void goToUsers(ActionEvent event){
        myController.setScreen(Main.screenUsers);
        reports.log("Go to Users Screen");
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);
        reports.log("Go to Login Screen");
    }
    @FXML
    private void goToAbout(ActionEvent event){
        myController.setScreen(Main.screenAbout);
        reports.log("Go to About Screen");
    }
}
