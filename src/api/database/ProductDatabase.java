/*
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

/*
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

package api.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductDatabase extends DatabaseConn implements Database {

    public ProductDatabase(){
        setDefaultDatabaseName();
        setDefaultDatabasePath();
    }
    @Override
    public Result create(String[] conditions, String[] arguments) {
        //Check for parameter errors
        if(arguments.length>1){
            return new Result(102);
        }else if(conditions.length!=arguments.length){
            return new Result(103);
        }else {
            int product_id = -1;
            int tax_index = -1;
            //Encapsulate in a try/catch to prevent database errors
            try{
                //This part transforms the parameters into a query that the database can handle
                String toSend="INSERT INTO PRODUCTS (";
                for(int i=0; (i<conditions.length && i<5); i++){
                    toSend=toSend+conditions[i];
                    //Adds a "," in each for and doesn't in the last loop
                    if(i!=(conditions.length-1))
                        toSend=toSend+", ";
                }
                toSend=toSend+") VALUES (";
                for(int i=0; (i<arguments.length && i<5); i++){
                    toSend=toSend+arguments[i];
                    //Adds a "," in each for and doesn't in the last loop
                    if(i!=(arguments.length-1))
                        toSend=toSend+", ";
                }
                toSend=toSend+")";


                //This part of the code makes the DATABASE CONNECTION
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + databasePath +"/"+ databaseName);
                c.setAutoCommit(false);

                //This part of the code makes the QUERY
                //This stament will look inside PRODUCTS TABLE
                pstmt = c.prepareStatement(toSend, Statement.RETURN_GENERATED_KEYS);
                pstmt.executeUpdate();
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                product_id = keys.getInt(1);
                keys.close();
                c.commit();
                pstmt.close();


                stmt = c.createStatement();
                toSend="INSERT INTO PRODUCTS_TAXES (product_id, percent) VALUES ("+product_id+","+arguments[5];
                stmt.addBatch(toSend);
                toSend="INSERT INTO PRODUCTS_QUANTITES (product_id, quantity) VALUES ("+product_id+","+arguments[6];
                stmt.addBatch(toSend);

                c.close();
            } catch ( Exception e ) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return new Result(100);
            }
            return new Result(200);
        }
    }


    //In this method there's only one parameter and it gives the barcode of the product
    @Override
    public Result select(String[] conditions, String[] arguments) {
        //Check for parameter errors
        if(arguments.length>1){
            return new Result(102);
        }else if(conditions.length!=arguments.length){
            return new Result(103);
        }else {
            String[] temp = new String[7];
            //Encapsulate in a try/catch to prevent database errors
            try{
                //This part transforms the parameters into a query that the database can handle
                String toSend="SELECT * FROM PRODUCTS WHERE ";
                for(int i=0; i<conditions.length; i++){
                    toSend=toSend+" "+conditions[i]+"="+arguments[i];
                    //Adds and AND in each for and doesn't in the last loop
                    if(i!=(conditions.length-1))
                        toSend=toSend+" AND ";
                }
                //This part of the code makes the DATABASE CONNECTION
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + databasePath +"/"+ databaseName);
                c.setAutoCommit(false);

                //This part of the code makes the QUERY
                //This stament will look inside PRODUCTS TABLE
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(toSend);
                while ( rs.next() ) {
                    temp[0] = rs.getString("product_id");
                    temp[1] = rs.getString("barcode");
                    temp[2] = rs.getString("name");
                    temp[3] = rs.getString("description");
                    temp[4] = rs.getString("price");
                }
                rs.close();
                stmt.close();

                //This stament will look inside PRODUCTS_TAXES TABLE
                toSend="SELECT * FROM PRODUCTS_TAXES WHERE product_id="+temp[0];
                stmt = c.createStatement();
                rs = stmt.executeQuery(toSend);
                while ( rs.next() ) {
                    temp[5] = rs.getString("percent");
                }
                rs.close();
                stmt.close();

                //This stament will look inside PRODUCTS_QUANTITIES TABLE
                toSend="SELECT * FROM PRODUCTS_QUANTITIES WHERE product_id="+temp[0];
                stmt = c.createStatement();
                rs = stmt.executeQuery(toSend);
                while ( rs.next() ) {
                    temp[5] = rs.getString("quantity");
                }
                rs.close();
                stmt.close();

                //Closes the database connection
                c.close();
            } catch ( Exception e ) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return new Result(100);
            }
            if(temp!=null)
                return new Result(temp);
            else
                return new Result(104);
        }
    }

    @Override
    public Result modify(String[] conditions, String[] arguments) {
        return null;
    }

    @Override
    public Result delete(String[] conditions, String[] arguments) {
        return null;
    }
}
