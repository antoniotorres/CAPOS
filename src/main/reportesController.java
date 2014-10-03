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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import properties.PropCapos;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class reportesController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        series.setName("Ventas del Mes");
        //populating the series with data
        LocalDate date = LocalDate.now();
        date = date.withDayOfMonth(1);
        int mSize = date.lengthOfMonth();
        LocalDate stop = date.withDayOfMonth(mSize);
        LocalDate cache = LocalDate.now();
        cache = date.withDayOfMonth(1);
        for(int x=0; x<=date.lengthOfMonth(); x++) {
            series.getData().add(new XYChart.Data(x+1, DbCapos.mesVentas(cache)));
            cache = cache.plusDays(1);
        }

        lChart.getData().add(series);
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    XYChart.Series series = new XYChart.Series();
    @FXML
    private TextField lVenta;
    @FXML
    private TextField lDinero;
    @FXML
    private TextField lTax;
    @FXML
    private DatePicker dFecha;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Number, Number> lChart;


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
    private void actualizar(ActionEvent event){
        lChart.getData().removeAll(series);
        series.setName("Ventas del Mes");
        //populating the series with data
        LocalDate date = LocalDate.now();
        date = date.withDayOfMonth(1);
        int mSize = date.lengthOfMonth();
        LocalDate stop = date.withDayOfMonth(mSize);
        LocalDate cache = LocalDate.now();
        cache = date.withDayOfMonth(1);
        for(int x=0; x<=date.lengthOfMonth(); x++) {
            series.getData().add(new XYChart.Data(x+1, DbCapos.mesVentas(cache)));
            cache = cache.plusDays(1);
        }

        lChart.getData().add(series);

        if(!(dFecha.getValue()==null)) {
            String[] valores = DbCapos.selectVentas(dFecha.getValue());
            lVenta.setText(valores[0]);
            lDinero.setText(valores[1]);
            PropCapos prop = new PropCapos();
            float temp = Float.parseFloat(valores[1]);
            lTax.setText(String.valueOf(temp-(temp/(1+prop.getTax()))));
        }

    }
}
