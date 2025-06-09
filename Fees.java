package StudentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;

public class Fees {
    private String programId;
    private String programName;
    private int fees;

    // Database credentials - change as per your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Jonsir@12345";

    public Fees(String programId, String programName, int fees) {
        this.programId = programId;
        this.programName = programName;
        this.fees = fees;
    }

    // Getters and setters
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public boolean addFees() {
        String sql = "INSERT INTO Fees (id, Programe_name, Fee) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stm = con.prepareStatement(sql)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            stm.setString(1, this.programId);
            stm.setString(2, this.programName);
            stm.setInt(3, this.fees);

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while adding Fees: " + e.getMessage());
            return false;
        }
    }

    public boolean updateFees() {
        String sql = "UPDATE Fees SET Programe_name = ?, Fee = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stm = con.prepareStatement(sql)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            stm.setString(1, this.programName);
            stm.setInt(2, this.fees);
            stm.setString(3, this.programId);

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating Fees: " + e.getMessage());
            return false;
        }
    }

    public Fees viewFeesDetails() {
        String sql = "SELECT Programe_name, Fee FROM Fees WHERE id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stm = con.prepareStatement(sql)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            stm.setString(1, this.programId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("Programe_name");
                int fees = rs.getInt("Fee");
                return new Fees(this.programId, name, fees);
            } else {
                return null; // No fees found with the given ID
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error while retrieving Fees details: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input fees details
        System.out.print("Enter Program ID: ");
        String programId = scanner.nextLine();
        System.out.print("Enter Program Name: ");
        String programName = scanner.nextLine();
        System.out.print("Enter Fees: ");
        
        int fees = 0;
        while (true) {
            try {
                fees = Integer.parseInt(scanner.nextLine());
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer for Fees: ");
            }
        }

        // Create a new Fees object
        Fees feesObj = new Fees(programId, programName, fees);

        // Attempt to add the fees to the database
        if (feesObj.addFees()) {
            System.out.println("Fees added successfully!");
        } else {
            System.out.println("Failed to add Fees.");
        }

        // Optionally, you can add more functionality to view or update fees here

        scanner.close();
    }
}
