package database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsersDB {
    //Intilize the variables needed for the class.
    private static Connection c = null;
    private static Statement stmt = null;
    private static File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private static String propertiesPath=jarPath.getParentFile().getAbsolutePath();
    private static String dbFile = propertiesPath+"/pos.db";

    public static void createDefaultUser(){
        if(!checkUser("admin")){
            insertUser("admin", "admin", "", "", 3);
        }
    }
    private static void insertUser(String u, String p, String fn, String ln, int level){
        try{
            java.util.Date date= new java.util.Date();
            String time = (new Timestamp(date.getTime()).toString());
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO users (username, password, first_name, last_name, level, created_on, updated_on) VALUES ('"+u+"','"+hashPassword(p)+"', '"+fn+"', '"+ln+"', '"+level+"', '"+time+"', '"+time+"');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public static boolean checkUser(String username) {
        boolean value=false;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME='"+username+"';");
            while ( rs.next() ) {
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
    public static boolean selectUser(String username, String password) {
        boolean value=false;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+hashPassword(password)+"';" );
            while ( rs.next() ) {
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
    //Encrypts User Password
    private static String hashPassword(String pwd){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pwd.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }

            digest = sb.toString();

        }  catch (NoSuchAlgorithmException ex) {
            System.out.println("Error Hash");
        }catch (UnsupportedEncodingException ex) {
            System.out.println("Error Hash");
        }
        return digest;
    }
}
