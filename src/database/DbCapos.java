package database;

import java.io.File;
import java.sql.*;

/**
 * Created by user on 6/28/14.
 */
public class DbCapos {
    public DbCapos() {
        checkDb();
    }
    public void checkDb() {
        //create("users", "username           TEXT    NOT NULL, password            TEXT     NOT NULL)");
        //insert("users", "USERNAME, PASSWORD", "'admin','admin'");
        //if (selectLogin("admin", "admins")) {
        //    System.out.println("TRUE");
        //}else {
        //   System.out.println("FALSe");
        //}


    }
    //Esta funcion crea una tabla dentro de la base de datos pos.db con los argumentos el nobmre de la tabla y los campos que van dentro de la tabla.
    public void create(String table, String statement) {
        Connection c = null;
        Statement stmt = null;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
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
    public void insert(String table, String column, String statement) {
        Connection c = null;
        Statement stmt = null;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO " + table + " (" + column + ") VALUES ( " + statement + " );";
            //String sql = "INSERT INTO " + table + " (NAME,AGE,ADDRESS,SALARY) " +
            //        "VALUES ('Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
    public static boolean selectLogin(String username, String password) {
        Connection c = null;
        Statement stmt = null;
        boolean value=false;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+password+"';" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("username");
                String  address = rs.getString("password");
                System.out.println( "ID = " + id );
                System.out.println( "USERNAME = " + name );
                System.out.println( "PASSWORD = " + address );
                System.out.println();
                value=true;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return value;
    }
    public static String[] selectProduct(String codigo) {
        Connection c = null;
        Statement stmt = null;
        String[] valor = new String[0];
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS WHERE codigo='"+codigo+"';" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  nombre = rs.getString("nombre");
                String  precio = rs.getString("precio");
                valor = new String[]{nombre, precio, codigo};
                System.out.println( "ID = " + id );
                System.out.println( "NOMBRe = " + nombre );
                System.out.println( "PRECIO = $" + precio );
                System.out.println( "CODIGO = " + precio );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        System.out.println("Operation done successfully");
        return valor;
    }
    public static boolean existeProducto(String codigo) {
        Connection c = null;
        Statement stmt = null;
        boolean value = false;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME='"+codigo+"';" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  nombre = rs.getString("nombre");
                String  precio = rs.getString("precio");
                System.out.println( "ID = " + id );
                System.out.println( "NOMBRe = " + nombre );
                System.out.println( "PRECIO = $" + precio );
                System.out.println( "CODIGO = " + precio );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return value;
    }
}
