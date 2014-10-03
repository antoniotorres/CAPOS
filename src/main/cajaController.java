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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

import database.DbCapos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

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
        colCantidad.setCellValueFactory( new PropertyValueFactory<Lista, String>("cantidad"));
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
    private TableColumn colCantidad;
    @FXML
    private TextField tSearch;
    @FXML
    private TextField tEfectivo;

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
        clear();
    }
    //Esta funcion al momento de buscar update la tabla y la informacion de venta
    @FXML
    private void addProduct(ActionEvent event){
        if (DbCapos.existeProducto(tSearch.getText())==true) {
            String[] valores = DbCapos.selectProduct(tSearch.getText());
            System.out.println(data.size());
            boolean found=false;
            for (int x=0; x< data.size(); x++) {
                Lista v = data.get(x);
                System.out.println(v.getCodigo());
                System.out.println(v.getCantidad());
                System.out.println(valores[1]);
                System.out.println(data.size());
                if (valores[1].equals(v.getCodigo())) {
                    int newc = v.getCantidad();
                    v.setCantidad(newc+1);

                    data.set(x, v);
                    found = true;
                    break;
                }
            }
            if (found!=true)
                data.add(new Lista(valores[0], valores[1], valores[2],1));
            addVenta(Float.parseFloat(valores[2]));
            cajaTable.setItems(data);
            tSearch.setText("");
        }
    }
    @FXML
    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (DbCapos.existeProducto(tSearch.getText())==true) {
                String[] valores = DbCapos.selectProduct(tSearch.getText());
                System.out.println(data.size());
                boolean found=false;
                for (int x=0; x< data.size(); x++) {
                    Lista v = data.get(x);
                    System.out.println(v.getCodigo());
                    System.out.println(v.getCantidad());
                    System.out.println(valores[1]);
                    System.out.println(data.size());
                    if (valores[1].equals(v.getCodigo())) {
                        int newc = v.getCantidad();
                        v.setCantidad(newc+1);

                        data.set(x, v);
                        found = true;
                        break;
                    }
                }
                if (found!=true)
                    data.add(new Lista(valores[0], valores[1], valores[2],1));
                addVenta(Float.parseFloat(valores[2]));
                cajaTable.setItems(data);
            }
            tSearch.setText("");
        }
    }
    @FXML
    private void endVenta (ActionEvent event){
        Float efectivo = 0.00f;
        try {
            efectivo=Float.parseFloat(tEfectivo.getText());

            if (efectivo < vTotal ) {
                throw new Exception ("");
            }
            if (data.isEmpty()){
                throw new Exception ("");
            }

            ArchivoBinario();

            //Cambia el color a blanco otraves si pasa los trys
            tEfectivo.setStyle("-fx-background-color: white;");

            myController.setScreen(Main.screenImprimir);
            clear();
            System.out.println("Go to Imprimir Screen");
        } catch (Exception e) {
            System.out.println("Error, solo se pueden numeros.");
            tEfectivo.setStyle("-fx-background-color: red;");
        }
    }

    //Este metodo  agregua los precios al subtotal, Impuesto y Total
    public void addVenta(float var){
        this.vSubtotal = this.vSubtotal + var;
        this.lSubtotal.setText("$ "+String.valueOf(this.vSubtotal));
        this.vTotal = this.vSubtotal + this.vTax;
        this.lTotal.setText("$ "+String.valueOf(this.vTotal));
    }
    public void clear (){
        data.removeAll(data);
        vSubtotal = 0.00f;
        vTax = 0.00f;
        vTotal = 0.00f;
        lSubtotal.setText("$0.00");
        lTax.setText("$0.00");
        lTotal.setText("$0.00");
        tSearch.setText("");
        tEfectivo.setText("");
        tEfectivo.setStyle("-fx-background-color: white;");
        System.out.println("Go to Caja Screen");
    }
    public void ArchivoBinario() {
        //Crear archive random llamado  nomina
        try
        { RandomAccessFile miArchivo=new RandomAccessFile("ArchivoBinario.dat","rw"); // rw significa que el archive se podrá leer  y escribir
            //inicializando variables para leer los datos de teclado
            String op="";
            int i;
            //Si hay espacios los elimina
            Float subtotal = vSubtotal;
            Float tax = vTax;
            Float total = vTotal;
            Float efectivo = Float.parseFloat(tEfectivo.getText());

            String[] nombre = new String[data.size()];
            String[] codigo = new String[data.size()];
            String[] precio = new String[data.size()];

            // Pasa la informacion del ArrayLista a un arreglo y le agregua espacios si no completa
            for(int x=0; x < codigo.length; x++ ){
                codigo[x]=data.get(x).getCodigo();
                if (codigo[x].length()<10){
                    for (int y=codigo[x].length(); y<10;y++)
                        codigo[x]=codigo[x] + " ";
                } else {
                    codigo[x]=codigo[x].substring(0,10);
                }

            }
            // Pasa la informacion del ArrayLista a un arreglo y le agregua espacios si no completa
            for(int x=0; x < nombre.length; x++ ){
                nombre[x]=data.get(x).getNombre();
                if (nombre[x].length()<10){
                    for (int y=nombre[x].length(); y<10;y++)
                        nombre[x]=nombre[x] + " ";
                } else {
                    nombre[x]=nombre[x].substring(0,10);
                }

            }
            // Pasa la informacion del ArrayLista a un arreglo y le agregua espacios si no completa
            for(int x=0; x < precio.length; x++ ){
                precio[x]=data.get(x).getPrecio();
                if (precio[x].length()<10){
                    for (int y=precio[x].length(); y<10;y++)
                        precio[x]=precio[x] + " ";
                } else {
                    precio[x]=precio[x].substring(0,10);
                }

            }

            for(int x=0; x < codigo.length; x++ ){
                System.out.println (nombre[x]+" "+codigo[x]+" "+precio[x]);
            }
            //grabando en el archivo
            if (miArchivo.length()!=0)
                miArchivo.seek(miArchivo.length()); // posiciona al final del archivo el cursor o apuntador
            //empieza a grabrar los datos en el archivo
            miArchivo.writeFloat(subtotal);
            miArchivo.writeFloat(tax);
            miArchivo.writeFloat(total);
            miArchivo.writeFloat(efectivo);
            for(int x=0; x < data.size(); x++ ) {
                miArchivo.writeChars(codigo[x]);
                miArchivo.writeChars(nombre[x]);
                miArchivo.writeChars(precio[x]);
            }


            System.out.println ("Venta registrada");
            miArchivo.close();  // cierra el archivo cuando ya no desea grabar mas
        } catch( IOException e) {
            System.out.println("ERROR -1: Archivo no encontrado");
        }
    }
}
