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

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import properties.PropCapos;

import java.net.URL;
import java.util.ResourceBundle;

public class settingsController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PropCapos prop = new PropCapos();
        cbLanguage.setItems(FXCollections.observableArrayList(
                "English (US)", "Espanol (Mexico)"));

        tbstorename.setText(prop.getStore_name());
        tbstorephone.setText(prop.getStore_phone());
        tbstoreaddress.setText(prop.getStore_address());
        tbtax.setText(prop.getTax().toString());
        tbticketmsg.setText(prop.getTicket_message());

        String a = prop.getLanguage();
        if (a.equals("en_US"))
            cbLanguage.setValue("English (US)");
        else if (a.equals("es_MX"))
            cbLanguage.setValue("Espanol (Mexico)");
        else
            cbLanguage.setValue("English (US)");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    ChoiceBox cbLanguage;
    @FXML
    TextField tbstorename;
    @FXML
    TextField tbstorephone;
    @FXML
    TextField tbstoreaddress;
    @FXML
    TextField tbtax;
    @FXML
    TextArea tbticketmsg;

    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(Main.screenMain);
    }
    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(Main.screenLogin);;
        System.out.println("Go to Login Screen");
    }
    @FXML
    private void update(ActionEvent event) {
        PropCapos prop = new PropCapos();
        //System.out.println(cbLanguage.getValue());
        String a = cbLanguage.getValue().toString();
        //Changes ChoiceBox value to abreviation ex_MX
        String b = "en_US";
        if(a.equals("English (US)"))
            b="en_US";
        else if(a.equals("Espanol (Mexico)"))
            b="es_MX";

        prop.setLanguage(b);
        prop.setStore_name(tbstorename.getText());
        prop.setStore_phone(tbstorephone.getText());
        prop.setStore_address(tbstoreaddress.getText());
        prop.setTicket_message(tbticketmsg.getText());
        try {
            prop.setTax(Float.parseFloat(tbtax.getText()));
        } catch (NumberFormatException e){
            System.out.println("Number Error");
            prop.setTax(0.00f);
        }
        prop.setData();
        System.out.println(prop.getLanguage());

    }
}
