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

package database;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;

import static database.CreateDB.*;
import static database.UsersDB.createDefaultUser;
import static database.UsersDB.selectUser;

public class DbCapos {

    //Initilize Variables
    private static Connection c = null;
    private static Statement stmt = null;

    public DbCapos() {
        //Makes the precheck of file
        checkDB();
    }
    public void checkDB() {
        create();
        createDefaultUser();
    }

    public static boolean login(String username, String password) {
        return selectUser(username, password);
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
}
