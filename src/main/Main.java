package main;

import api.database.Database;
import api.database.ProductDatabase;
import api.database.Result;
import database.DbCapos;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import properties.PropCapos;

public class Main extends Application {


    public static String screenMain = "main";
    public static String screenMain_FXML = "fxml/ScreenMain.fxml";
    public static String screenLogin = "login";
    public static String screenLogin_FXML = "fxml/ScreenLogin.fxml";
    public static String screenCaja = "caja";
    public static String screenCaja_FXML = "fxml/ScreenCaja.fxml";
    public static String screenImprimir = "imprimir";
    public static String getScreenImprimir_FXML = "fxml/ScreenImprimir.fxml";
    public static String screenReportes = "reportes";
    public static String getScreenReportes_FXML = "fxml/ScreenReportes.fxml";
    public static String screenInventario = "inventario";
    public static String getScreenInventario_FXML = "fxml/ScreenInventario.fxml";
    public static String screenSettings = "settings";
    public static String getScreenSettings_FXML = "fxml/ScreenSettings.fxml";
    public static String screenUsers = "users";
    public static String getScreenUsers_FXML = "fxml/ScreenUsers.fxml";
    public static String screenAbout = "about";
    public static String getScreenAbout_FXML = "fxml/ScreenAbout.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        PropCapos dprop = new PropCapos();
        DbCapos database = new DbCapos();

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screenMain, Main.screenMain_FXML);
        mainContainer.loadScreen(Main.screenLogin, Main.screenLogin_FXML);
        mainContainer.loadScreen(Main.screenCaja, Main.screenCaja_FXML);
        mainContainer.loadScreen(Main.screenImprimir, Main.getScreenImprimir_FXML);
        mainContainer.loadScreen(Main.screenReportes, Main.getScreenReportes_FXML);
        mainContainer.loadScreen(Main.screenInventario, Main.getScreenInventario_FXML);
        mainContainer.loadScreen(Main.screenSettings, Main.getScreenSettings_FXML);
        mainContainer.loadScreen(Main.screenUsers, Main.getScreenUsers_FXML);
        mainContainer.loadScreen(Main.screenAbout, Main.getScreenAbout_FXML);

        mainContainer.setScreen(Main.screenLogin);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("CAPOS - " + dprop.getStore_name());
        primaryStage.getIcons().add(new Image("/res/icons/logo/logo-64x64.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
    //Esta parte del codigo inicia los archivos que estan fuera del programa.
}
