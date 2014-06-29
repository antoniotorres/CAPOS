package properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by user on 6/28/14.
 */
public class PropCapos {
    private String path;
    public PropCapos() {
        path = checkFile();

    }
    //Este metodo checa si el archivo ya existe, sino se va al metodo que crea el archivo.
    private String checkFile(){
        OutputStream output = null;
        Properties prop = new Properties();

        File jarPath=new File(PropCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();

        File caposProp = new File(propertiesPath+"/capos.properties");
        System.out.println("Cargando Archivo de Propiedades: "+caposProp.getPath());
        if(!caposProp.exists()) {
            try {
                System.out.println("Creando Archivo");

                output = new FileOutputStream(caposProp);

                // set the properties value
                prop.setProperty("database", "localhost");
                prop.setProperty("dbuser", "capos");
                prop.setProperty("dbpassword", "capos");
                prop.setProperty("dname", "capos");
                prop.setProperty("dtype", "sqlite");

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
        return caposProp.getPath();
    }
}
