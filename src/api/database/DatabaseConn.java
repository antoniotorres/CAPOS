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

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseConn {
    private String databasePath = null;
    private String databaseName = null;
    private Connection c = null;
    private Statement stmt = null;

    private void setDefaultDatabasePath(){
        File jarPath=new File(DatabaseConn.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        databasePath=jarPath.getParentFile().getAbsolutePath();
    }
    private void setDefaultDatabaseName(){
        databasePath="pos.db";
    }
}
