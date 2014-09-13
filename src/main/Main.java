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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import properties.PropCapos;

public class Main extends Application {


    public static String screenMain = "main";
    public static String screenMain_FXML = "ScreenMain.fxml";
    public static String screenLogin = "login";
    public static String screenLogin_FXML = "ScreenLogin.fxml";
    public static String screenCaja = "caja";
    public static String screenCaja_FXML = "ScreenCaja.fxml";
    public static String screenImprimir = "imprimir";
    public static String getScreenImprimir_FXML = "ScreenImprimir.fxml";
    public static String screenReportes = "reportes";
    public static String getScreenReportes_FXML = "ScreenReportes.fxml";
    public static String screenInventario = "inventario";
    public static String getScreenInventario_FXML = "ScreenInventario.fxml";

    PropCapos dprop = new PropCapos();
    DbCapos database = new DbCapos();

    @Override
    public void start(Stage primaryStage) throws Exception{

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screenMain, Main.screenMain_FXML);
        mainContainer.loadScreen(Main.screenLogin, Main.screenLogin_FXML);
        mainContainer.loadScreen(Main.screenCaja, Main.screenCaja_FXML);
        mainContainer.loadScreen(Main.screenImprimir, Main.getScreenImprimir_FXML);
        mainContainer.loadScreen(Main.screenReportes, Main.getScreenReportes_FXML);
        mainContainer.loadScreen(Main.screenInventario, Main.getScreenInventario_FXML);

        mainContainer.setScreen(Main.screenLogin);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("CAPOS - "+dprop.getStore_name());
        primaryStage.getIcons().add(new Image("/res/icons/logo/logo-64x64.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
    //Esta parte del codigo inicia los archivos que estan fuera del programa.
}
