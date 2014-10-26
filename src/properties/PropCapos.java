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

package properties;

import java.io.*;
import java.util.Properties;

public class PropCapos {

    private String store_name;
    private String store_address;
    private String store_phone;
    private String store_logo;
    private String database_type;
    private String database_user;
    private String database_password;
    private String database_name;
    private String database_url;
    private String ticket_message;
    private Float tax;

    public PropCapos() {
        checkFile();
        loadData();

    }
    //Este metodo checa si el archivo ya existe, sino se va al metodo que crea el archivo.
    private void checkFile(){
        File jarPath=new File(PropCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        Properties prop = new Properties();

        OutputStream output = null;
        File caposProp = new File(propertiesPath+"/capos.properties");
        System.out.println("Cargando Archivo de Propiedades: "+caposProp.getPath());
        if(!caposProp.exists()) {
            try {
                System.out.println("capos.properties doesn't exists!");
                System.out.println("Creating File");

                output = new FileOutputStream(caposProp);

                // set the properties value
                prop.setProperty("database_url", "localhost");
                prop.setProperty("database_buser", "capos");
                prop.setProperty("database_password", "capos");
                prop.setProperty("database_name", "capos");
                prop.setProperty("database_type", "sqlite");
                prop.setProperty("store_logo", "logo.png");
                prop.setProperty("store_name", "Store Name");
                prop.setProperty("store_address", "Test Street 123, Test City, Test Country");
                prop.setProperty("store_phone", "12341234");
                prop.setProperty("ticket_message", "Thank you for shopping in our store.");
                prop.setProperty("tax", "0.00");

                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    private void loadData(){
        File jarPath=new File(PropCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(propertiesPath+"/capos.properties"));
        }catch(Exception e){
            System.out.println("file not found.");
            e.printStackTrace();
        }
        store_name = prop.getProperty("store_name");
        store_logo = prop.getProperty("store_logo");
        database_type = prop.getProperty("database_type");
        database_name = prop.getProperty("database_name");
        database_user = prop.getProperty("database_user");
        database_url = prop.getProperty("database_url");
        database_password = prop.getProperty("database_password");
        store_address = prop.getProperty("store_address");
        store_phone = prop.getProperty("store_phone");
        ticket_message = prop.getProperty("ticket_message");
        try {
            tax = Float.parseFloat(prop.getProperty("tax"));
        } catch (NumberFormatException e){
            System.out.println("Error");
        }
    }
    public String getStore_logo(){
        return store_logo;
    }
    public String getDatabase_type(){
        return database_type;
    }
    public String getDatabase_user() {
        return database_user;
    }

    public String getDatabase_password() {
        return database_password;
    }

    public String getDatabase_url() {
        return database_url;
    }

    public String getDatabase_name(){
        return database_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public String getTicket_message() {
        return ticket_message;
    }

    public Float getTax() {
        return tax;
    }
}
