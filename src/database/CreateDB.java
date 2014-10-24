package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
*    This file is part of CAPOS
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class CreateDB {
    //Intilize the variables needed for the class.
    private static Connection c = null;
    private static Statement stmt = null;
    private static File jarPath=new File(DbCapos.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private static String propertiesPath=jarPath.getParentFile().getAbsolutePath();
    private static String dbFile = propertiesPath+"/postest.db";

    public static boolean lookFile(){
        File check = new File(dbFile);
        return check.exists();
    }

    public static void create(){
        if(lookFile()!=true) {
            //Creates the tables necessary for the program
            newTable("productos", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT, \"nombre\" TEXT NOT NULL,\n" +
                    "    \"codigo\" TEXT NOT NULL,\n" +
                    "    \"precio\" REAL NOT NULL,\n" +
                    "    \"cantidad\" INTEGER NOT NULL)");

            newTable("users", "(ID INTEGER PRIMARY KEY     AUTOINCREMENT, username           TEXT    NOT NULL, password            TEXT     NOT NULL)");

            newTable("ventas", "( \"sale_id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    \"sale_time\" TEXT NOT NULL,\n" +
                    "    \"payment_type\" TEXT NOT NULL,\n" +
                    "    \"payment_amount\" REAL NOT NULL)");
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
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "CREATE TABLE " + table + " " + statement;
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
