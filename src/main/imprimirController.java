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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

public class imprimirController extends ControlledScreen implements Initializable {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lCambio.setText("");
        bRegresar.setVisible(false);

    }

    @FXML
    private Label lCambio;
    @FXML
    private Button bRegresar;
    @FXML
    private Button bImprimir;

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToCaja(ActionEvent event){
        myController.setScreen(Main.screenCaja);
        System.out.println("Go to Caja Screen");
        bRegresar.setVisible(false);
        bImprimir.setVisible(true);
        lCambio.setText("");
    }
    @FXML
    private void imprimir(ActionEvent event){
        File file = new File("ArchivoBinario.dat");
        if (file.exists()) {
            bRegresar.setVisible(true);
            bImprimir.setVisible(false);
            update();
        }
    }
    public void update() {
        //Crear archive random llamado  nomina
        try
        {
            RandomAccessFile miArchivo=new RandomAccessFile("ArchivoBinario.dat","rw"); // rw significa que el archive se podrá leer  y escribir
            //inicializando variables para leer los datos de teclado
            String op="";
            int i;
            int tamRegistro=120;
            Long l = miArchivo.length();
            int cantRegistros=(((Integer.valueOf(l.intValue()))-16)/tamRegistro);
            miArchivo.seek(0);
            Float subtotal = miArchivo.readFloat();
            Float tax = miArchivo.readFloat();
            Float total = miArchivo.readFloat();
            Float efectivo = miArchivo.readFloat();
            System.out.println (subtotal+" "+tax+" "+total+" "+l+" "+cantRegistros+" "+"deb6");
            String[] nombre = new String[cantRegistros];
            String[] codigo = new String[cantRegistros];
            String[] precio = new String[cantRegistros];
            // Pasa la informacion del ArrayLista a un arreglo y le agregua espacios si no completa
            for(int x=0; x < cantRegistros; x++ ){
                codigo[x]="";
                for (int j=0; j<20;j++)
                    codigo[x]= codigo[x] + miArchivo.readChar();
                nombre[x]="";
                for (int j=0; j<20;j++)
                    nombre[x]= nombre[x] + miArchivo.readChar();
                precio[x]="";
                for (int j=0; j<20;j++)
                    precio[x]= precio[x] + miArchivo.readChar();
            }
            for(int x=0; x < codigo.length; x++ ){
                System.out.println (nombre[x]+" "+codigo[x]+" "+precio[x]);
            }

            System.out.println ("Venta registrada");
            miArchivo.close();  // cierra el archivo cuando ya no desea grabar mas




            lCambio.setText("$"+Float.toString(efectivo-total));


            DbCapos.insertVenta("Efectivo", total);




        } catch( IOException e) {
            System.out.println("ERROR -1: Archivo no encontrado");
        } finally {
            File file = new File("ArchivoBinario.dat");
            file.delete();
        }

    }
}
