package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDB {
    //Intilize the variables needed for the class.
    private static Connection c = null;
    private static Statement stmt = null;
    private static File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private static String propertiesPath=jarPath.getParentFile().getAbsolutePath();
    private static String dbFile = propertiesPath+"/pos.db";

    public static boolean lookFile(){
        File check = new File(dbFile);
        return check.exists();
    }

    public static void create(){
        if(!lookFile()) {
            //Creates the tables necessary for the program
            System.out.println("pos.db doesn't exist. Creating new file.");
            newTable("productos", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT, \"nombre\" TEXT NOT NULL,\n" +
                    "    \"codigo\" TEXT NOT NULL,\n" +
                    "    \"precio\" REAL NOT NULL,\n" +
                    "    \"cantidad\" INTEGER NOT NULL)");

            newTable("users", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                    " username           TEXT    NOT NULL," +
                    " password            TEXT     NOT NULL," +
                    " first_name            TEXT     NOT NULL," +
                    " last_name            TEXT     NOT NULL," +
                    " level            INTEGER     NOT NULL," +
                    " created_on            TEXT     NOT NULL," +
                    " updated_on            TEXT     NOT NULL)");


            newTable("ventas", "( \"sale_id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    \"sale_time\" TEXT NOT NULL,\n" +
                    "    \"payment_type\" TEXT NOT NULL,\n" +
                    "    \"payment_amount\" REAL NOT NULL)");
            System.out.println("Successfully created pos.db file.");
        }
    }
    public static boolean checkTables(){

        return false;
    }

    //Crea nuevas tablas en la base de datos (SQLITE)
    private static void newTable(String table, String statement){
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            stmt = c.createStatement();
            String sql = "CREATE TABLE " + table + " " + statement;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
