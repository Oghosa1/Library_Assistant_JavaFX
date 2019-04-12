package library.asssistance.database;

//import org.apache.derby.impl.sql.compile.TableName;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DatabaseHandler {
   private static DatabaseHandler databaseHandler = null;


    private static final String DB_URL = "jdbc:derby:database;create=true;";
    private static Connection conn = null;
    private static Statement stmt = null;

    public static DatabaseHandler getInstance(){
        if(databaseHandler == null){
            databaseHandler = new DatabaseHandler();
        }
        return databaseHandler;
    }

    public DatabaseHandler() {
        createConnection();
        SetupBookTable();
        SetupMemberTable();
        setUpIssueTable();
    }

    void createConnection() {


        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    void SetupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + "  already exist. Ready to go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       id varchar(200) primary key ,\n"
                        + "       name varchar(200),\n"
                        + "       mobile varchar(20),\n"
                        + "       email varchar(100)\n"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "   ... setupDatabase");
        } finally {

        }
        System.out.println("  success ");
    }

    void setUpIssueTable(){
        String TABLE_NAME = "ISSUE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + "  already exist.Ready to go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       bookID varchar(200) primary key ,\n"
                        + "       memberID varchar(200),\n"
                        + "       issueTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "       renew_count integer default 0,\n"
                        + "       FOREIGN KEY (bookID)REFERENCES BOOK(id),\n"
                        + "       FOREIGN KEY (memberID)REFERENCES MEMBER(id)"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "   ... setupDatabase");
        } finally {

        }
        System.out.println("  success ");
    }


    void SetupBookTable() {
        String TABLE_NAME = "BOOK";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + "  already exist.Ready to go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "       id varchar(200) primary key ,\n"
                        + "       title varchar(200),\n"
                        + "       author varchar(200),\n"
                        + "       publisher varchar(100),\n"
                        + "       isAvail boolean default true "
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "   ... setupDatabase");
        } finally {

        }
        System.out.println("  success ");
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {

            result = stmt.executeQuery(query);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Exception at exeQuery:dataHandler" + e.getLocalizedMessage());
            return null;
        } finally {

        }
        return result;

    }

    //This is to check if execQuery was successful or not
    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at exceQuery: dataHandler" + e.getLocalizedMessage());
            return false;
        } finally {

        }

    }

}
