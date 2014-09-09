package database;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;

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
                String  cantidad = rs.getString("cantidad");
                valor = new String[]{nombre, precio, codigo, cantidad};
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
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS WHERE CODIGO='"+codigo+"';" );
            while ( rs.next() ) {
               value = true;
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
    public static void insertProducto(String codigo, String nombre, Float precio, int cantidad) {
        Connection c = null;
        Statement stmt = null;
        java.util.Date date= new java.util.Date();
        String time = DateFormat.getDateInstance().format(new Timestamp(date.getTime()));
        System.out.println(time);
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO PRODUCTOS (nombre,codigo,precio,cantidad) " +
                    "VALUES ( '"+nombre+"','" +codigo+"',"+precio+","+cantidad+");";
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
    public static void deleteProducto(String codigo) {
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
            String sql = "DELETE FROM PRODUCTOS WHERE CODIGO='"+codigo+"';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Delete done successfully");
    }
    public static void insertVenta(String tipo, Float cantidad) {
        Connection c = null;
        Statement stmt = null;
        java.util.Date date= new java.util.Date();
        String time = DateFormat.getDateInstance().format(new Timestamp(date.getTime()));
        System.out.println(time);
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO VENTAS (sale_time,payment_type,payment_amount) " +
                    "VALUES ( '"+new Timestamp(date.getTime())+"','" +tipo+"',"+cantidad+");";
            //String sql = "INSERT INTO " + table + " (NAME,AGE,ADDRESS,SALARY) " +
            //        "VALUES ('Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            while ( rs.next() ) {
                int id = rs.getInt(1);
                System.out.println(id);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
    public static String[] selectVentas(LocalDate date) {
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
            LocalDate stop = date.plusDays(1);
            ResultSet rs = stmt.executeQuery( "SELECT * FROM VENTAS where sale_time >= strftime('%Y-%m-%d %H:%M:%S.%f', '"+date+"') and sale_time < strftime('%Y-%m-%d %H:%M:%S.%f', '"+stop+"');" );
            int x =0;
            float y=0.00f;
            while ( rs.next() ) {
                y= y+rs.getFloat("payment_amount");
                x++;
            }
            valor = new String[]{String.valueOf(x), String.valueOf(y)};
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
    public static float mesVentas(LocalDate date) {
        Connection c = null;
        Statement stmt = null;
        float valor = 0;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            LocalDate stop = date.plusDays(1);
            ResultSet rs = stmt.executeQuery( "SELECT * FROM VENTAS where sale_time >= strftime('%Y-%m-%d %H:%M:%S.%f', '"+date+"') and sale_time < strftime('%Y-%m-%d %H:%M:%S.%f', '"+stop+"');" );
            int x =0;
            float y=0.00f;
            while ( rs.next() ) {
                y= y+rs.getFloat("payment_amount");
                x++;
            }
            valor = y;
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return valor;
    }
}
