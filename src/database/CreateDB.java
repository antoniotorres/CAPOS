package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Autor: Jose Antonio Torres
 * CAPOS - Punto de Venta
 * GNU
 */
public class CreateDB {
    //Intilize the variables needed for the class.
    private static Connection c = null;
    private static Statement stmt = null;
    static File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    static String propertiesPath=jarPath.getParentFile().getAbsolutePath();

    public static boolean create(){
        boolean success=false;


        return success;
    }

    //Crea nuevas tablas en la base de datos (SQLITE)
    private static void newTable(String table, String statement){
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + propertiesPath + "/pos.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "CREATE TABLE " + table + " " +
                    "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                    statement;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

}
