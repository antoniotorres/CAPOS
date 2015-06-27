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

public class ProductDatabase extends DatabaseConn implements Database {

    public ProductDatabase(){
        setDefaultDatabaseName();
        setDefaultDatabasePath();
    }
    @Override
    public Result create(String[] arguments) {
        return null;
    }


    //In this method there's only one parameter and it gives the barcode of the product
    @Override
    public Result select(String[] conditions, String[] arguments) {
        //Check for parameter errors
        if(arguments.length>1){
            return new Result(102);
        } else {
            String[] temp = null;
            //Encapsulate in a try/catch to prevent database errors
            try{
                //This part of the code makes the DATABASE CONNECTION
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + databasePath + databaseName);
                c.setAutoCommit(false);

                //This part of the code makes the QUERY
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM PRODUCTS WHERE barcode='"+arguments[0]+"';" );
                while ( rs.next() ) {
                    temp = new String[]{rs.getString("product_id"), arguments[0], rs.getString("name"), rs.getString("price")};
                }
                rs.close();
                stmt.close();
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
    public Result modify(String[] arguments) {
        return null;
    }

    @Override
    public Result delete(String[] arguments) {
        return null;
    }
}
