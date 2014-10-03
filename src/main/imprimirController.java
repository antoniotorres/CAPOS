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
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import properties.PropCapos;

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
        text.setFont(new Font(10));
        text.setTextAlignment(TextAlignment.CENTER);
        PropCapos prop = new PropCapos();
        text.setText("\n"+prop.getStore_name()+"\n"+prop.getStore_address()+"\n\n");
        text.setText(text.getText()+"Codigo\tPrecio\tPrecio\tCantidad \n");

    }

    @FXML
    private Label lCambio;
    @FXML
    private Button bRegresar;
    @FXML
    private Button bImprimir;
    Text text = new Text();

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToCaja(ActionEvent event){
        myController.setScreen(Main.screenCaja);
        System.out.println("Go to Caja Screen");
        bRegresar.setVisible(false);
        bImprimir.setVisible(true);
        bImprimir.setDisable(false);
        lCambio.setText("");
    }
    @FXML
    private void imprimir(ActionEvent event){
        File file = new File("ArchivoBinario.dat");
        if (file.exists()) {
            bRegresar.setVisible(true);
            bImprimir.setVisible(false);
            update();
            print();
            bImprimir.setDisable(true);
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
            int tamRegistro=64;
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
            int[] cantidad = new int[cantRegistros];
            // Pasa la informacion del ArrayLista a un arreglo y le agregua espacios si no completa
            for(int x=0; x < cantRegistros; x++ ){
                codigo[x]="";
                for (int j=0; j<10;j++)
                    codigo[x]= codigo[x] + miArchivo.readChar();
                nombre[x]="";
                for (int j=0; j<10;j++)
                    nombre[x]= nombre[x] + miArchivo.readChar();
                precio[x]="";
                for (int j=0; j<10;j++)
                    precio[x]= precio[x] + miArchivo.readChar();

                cantidad[x]=miArchivo.readInt();
            }
            System.out.println (cantRegistros +" ");
            for(int x=0; x < codigo.length; x++ ){
                System.out.println (nombre[x]+" "+codigo[x]+" "+precio[x]);
                text.setText(text.getText()+nombre[x]+"\t"+codigo[x]+"\t"+precio[x]+"\t"+cantidad[x]+"\n");
            }

            System.out.println ("Venta registrada");
            miArchivo.close();  // cierra el archivo cuando ya no desea grabar mas


            PropCapos prop = new PropCapos();

            lCambio.setText("$"+Float.toString(efectivo-total));
            text.setText(text.getText()+"\n\nSubtotal: $"+subtotal+"\n");
            text.setText(text.getText()+"IVA: $"+tax+"\n");
            text.setText(text.getText()+"Total: $"+total+"\n");
            text.setText(text.getText()+"Efectivo: $"+efectivo+"\n");
            text.setText(text.getText()+"Cambio: $"+Float.toString(efectivo-total)+"\n");
            text.setText(text.getText()+prop.getTicket_message());

            for(int x=0; x < codigo.length; x++ ){
                codigo[x]=codigo[x].trim();
            }
            DbCapos.insertVenta("Efectivo", total, codigo, cantidad);




        } catch( IOException e) {
            System.out.println("ERROR -1: Archivo no encontrado");
        } finally {
            File file = new File("ArchivoBinario.dat");
            file.delete();
        }

    }
    public void print (){
        Stage stage=(Stage) lCambio.getScene().getWindow();
        Node node = text;
        PrinterJob job = PrinterJob.createPrinterJob();
        //job.showPageSetupDialog(stage);
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
}
