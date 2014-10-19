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
    private static Connection c = null;
    private static Statement stmt = null;
    //Esta funcion crea una tabla dentro de la base de datos pos.db con los argumentos el nobmre de la tabla y los campos que van dentro de la tabla.
    public void create(String table, String statement) {
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
        boolean value=false;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+password+"';" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("username");
                String  address = rs.getString("password");
                value=true;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return value;
    }
    public static String[] selectProduct(String codigo) {
        String[] valor = new String[0];
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS WHERE codigo='"+codigo+"';" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  nombre = rs.getString("nombre");
                String  precio = rs.getString("precio");
                String  cantidad = rs.getString("cantidad");
                valor = new String[]{nombre, codigo, precio, cantidad};
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return valor;
    }
    public static String[] selectProduct(int id) {
        String[] valor = null;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS WHERE ID='"+id+"';" );
            while ( rs.next() ) {
                String  nombre = rs.getString("nombre");
                String  precio = rs.getString("precio");
                String  cantidad = rs.getString("cantidad");
                String  codigo = rs.getString("codigo");
                valor = new String[]{nombre, precio, codigo, cantidad};
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return valor;
    }
    public static boolean existeProducto(String codigo) {
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
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE FROM PRODUCTOS WHERE codigo='"+codigo+"';";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Delete done successfully");
    }
    public static void insertVenta(String tipo, Float cantidad, String[] codigo, int[] inventario) {
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

        int[] invNow = new int[inventario.length];

        for (int x=0; x< invNow.length; x++){
            invNow[x]= numInventario(codigo[x]);
        }
        for (int x=0; x<inventario.length; x++) {
            try {
                File jarPath = new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                String propertiesPath = jarPath.getParentFile().getAbsolutePath();

                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + propertiesPath + "/pos.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                int var = invNow[x] - inventario[x];
                stmt = c.createStatement();
                String sql = "UPDATE PRODUCTOS set CANTIDAD='" + var + "' where CODIGO='" + codigo[x]+"';";
                stmt.executeUpdate(sql);

                stmt.close();
                c.commit();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }

    }
    public static int numInventario(String codigo) {
        int valor = 0;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS where CODIGO='" + codigo+"';" );
            int x =0;
            while ( rs.next() ) {
                x = rs.getInt("cantidad");
            }
            valor = x;
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return valor;
    }
    public static void updateProducto(String codigo, String nombre, float precio, int cant) {
        java.util.Date date= new java.util.Date();
        String time = DateFormat.getDateInstance().format(new Timestamp(date.getTime()));
        System.out.println(time);
        try {
            File jarPath = new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + propertiesPath + "/pos.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE PRODUCTOS set NOMBRE='"+nombre+"', PRECIO='"+precio+"', CANTIDAD='"+cant+"' where CODIGO='"+codigo+"';";
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
    public static String[] selectVentas(LocalDate date) {
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
    public static int[] numProductos() {
        int[] x =null;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS;" );
            int y =0;
            while ( rs.next() ) {
                y++;
            }
            x = new int[y];
            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM PRODUCTOS;" );
            y =0;
            while ( rs.next() ) {
                x[y]= rs.getInt("id");
                y++;
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        System.out.println("Operation done successfully");
        return x;
    }
    public static float mesVentas(LocalDate date) {
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

    public static String[] selectDescuento(int id) {
        String[] valor = null;
        try {
            File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+propertiesPath+"/pos.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM DESCUENTO WHERE ID='"+id+"';" );
            while ( rs.next() ) {
                String  dId = rs.getString("id");
                String  codigo = rs.getString("codigo");
                String  cantidad = rs.getString("cantidad");
                String  descuento = rs.getString("descuento");
                valor = new String[]{dId, codigo, cantidad, descuento};
            }
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
