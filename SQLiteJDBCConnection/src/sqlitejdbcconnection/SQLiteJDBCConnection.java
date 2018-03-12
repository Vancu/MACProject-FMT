/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitejdbcconnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kylan
 */
public class SQLiteJDBCConnection {

    public Connection connect() {
        String url = "jdbc:sqlite:X:\\kylan\\Desktop\\Database\\FMTDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver name: " + meta.getDriverName());
                System.out.println("Connected.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void newTable(String name) {
        String url = "jdbc:sqlite:X:\\kylan\\Desktop\\Database\\FMTDB.db";
        String sql = "CREATE TABLE IF NOT EXISTS " + name + "(\n"
                    + "id int, \n"
                    + "name text, \n"
                    + "value double \n"
                    + ");";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement();) {
            stmt.execute(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "1");
        }
        
    }
    
    public void insert(String table, int id, String name, double value) {
        String sql = "INSERT INTO " + table + "(id, name, value) VALUES(?, ?, ?)";
        
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, value);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "2");
        }
    }
    
    public void update(String table, int id, String name, double value){
        String sql = "UPDATE " + table + " SET name = ?, value = ? WHERE id = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           pstmt.setDouble(2, value);
           pstmt.setInt(3, id);
           pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "3");
        }
    }
    
    public void delete(String table, int id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "4");
        }
    }
    
    public void select(String table){
        String sql = "SELECT id, name, value FROM " + table;
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("value"));
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage() + "5");
        }
    }
    
    public static void main(String[] args) {
        String cont = "y";
        SQLiteJDBCConnection app = new SQLiteJDBCConnection();
        app.connect();
        System.out.println("Input a new table? Y/N");
        Scanner scan = new Scanner(System.in);
        String InputYN = scan.nextLine();
        if ("y".equals(InputYN)) {
            System.out.println("Table name to input: ");
            scan = new Scanner(System.in);
            String TableName = scan.nextLine();
            app.newTable(TableName);
        }
        System.out.println("Input new data? Y/N");
        scan = new Scanner(System.in);
        String DataYN = scan.nextLine();
        int id = 1;
        while ("y".equals(DataYN) && "y".equals(cont)){
            System.out.println("Table to edit: ");
            scan = new Scanner(System.in);
            String TableName = scan.nextLine();
            System.out.println("Name of Data: ");
            scan = new Scanner(System.in);
            String DataName = scan.nextLine();
            System.out.println("Amount: ");
            scan = new Scanner(System.in);
            double Amount = scan.nextDouble();
            app.insert(TableName, id, DataName, Amount);
            System.out.println("Continue? Y/N");
            scan = new Scanner(System.in);
            cont = scan.nextLine();
            id++;
        }
        System.out.println("Update any data? Y/N");
        scan = new Scanner(System.in);
        String UpdateYN = scan.nextLine();
        if ("y".equals(UpdateYN)){
            System.out.println("Table to edit: ");
            scan = new Scanner(System.in);
            String TableName = scan.nextLine();
            System.out.println("Where? Id number:");
            scan = new Scanner(System.in);
            int Id = scan.nextInt();
            System.out.println("New Name of Data: ");
            scan = new Scanner(System.in);
            String DataName = scan.nextLine();
            System.out.println("New Amount: ");
            scan = new Scanner(System.in);
            double Amount = scan.nextDouble();
            app.update(TableName, Id, DataName, Amount);
        }
        System.out.println("Delete any data? Y/N");
        scan = new Scanner(System.in);
        String DeleteYN = scan.nextLine();
        if ("y".equals(DeleteYN)){
            System.out.println("Table to edit: ");
            scan = new Scanner(System.in);
            String TableName = scan.nextLine();
            System.out.println("Where? Id number:");
            scan = new Scanner(System.in);
            int Id = scan.nextInt();
            app.delete(TableName, Id);
        }
        System.out.println("Select all data? Y/N");
        scan = new Scanner(System.in);
        String SelectYN = scan.nextLine();
        if ("y".equals(SelectYN)) {
            System.out.println("Table to select: ");
            scan = new Scanner(System.in);
            String TableName = scan.nextLine();
            app.select(TableName);
        }
    }
}
