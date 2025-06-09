package StudentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;

public class Performance {

    private String studentId;
    private String courseName;
    private String obtainMarks;
    private String maximumMarks;
    private String totalMarks;

    // Database credentials - change as per your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Jonsir@12345";

    public Performance(String studentId, String courseName, String obtainMarks, String maximumMarks, String totalMarks) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.obtainMarks = obtainMarks;
        this.maximumMarks = maximumMarks;
        this.totalMarks = totalMarks;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getObtainMarks() {
        return obtainMarks;
    }

    public void setObtainMarks(String obtainMarks) {
        this.obtainMarks = obtainMarks;
    }

    public String getMaximumMarks() {
        return maximumMarks;
    }

    public void setMaximumMarks(String maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    /**
     * Adds the performance record to the database.
     * @return true if insert was successful, false otherwise
     */
    public boolean addPerformance() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "INSERT INTO performance (student_id, course_name, obtain_marks, maximum_marks, total_marks) VALUES (?, ?, ?, ?, ?)";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.studentId);
            stm.setString(2, this.courseName);
            stm.setString(3, this.obtainMarks);
            stm.setString(4, this.maximumMarks);
            stm.setString(5, this.totalMarks);

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while adding performance record.");
            e.printStackTrace();
            return false;
        } finally {
            // Clean up resources
            try {
                if (stm != null) stm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the performance record in the database.
     * @return true if update was successful, false otherwise
     */
    public boolean updatePerformance() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "UPDATE performance SET course_name = ?, obtain_marks = ?, maximum_marks = ?, total_marks = ? WHERE student_id = ?";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.courseName);
            stm.setString(2, this.obtainMarks);
            stm.setString(3, this.maximumMarks);
            stm.setString(4, this.totalMarks);
            stm.setString(5, this.studentId);

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating performance record.");
            e.printStackTrace();
            return false;
        } finally {
            // Clean up resources
            try {
                if (stm != null) stm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Views the performance details from the database.
     * @return Performance object if found, null otherwise
     */
    public Performance viewPerformanceDetails() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; // Declare ResultSet here
        String sql = "SELECT course_name, obtain_marks, maximum_marks, total_marks FROM performance WHERE student_id = ?";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.studentId); // Set the student ID for the WHERE clause

            // Execute query
            rs = stm.executeQuery();

            // Check if a result was returned
            if (rs.next()) {
                // Create a new Performance object with the retrieved details
                String courseName = rs.getString("course_name");
                String obtainMarks = rs.getString("obtain_marks");
                String maximumMarks = rs.getString("maximum_marks");
                String totalMarks = rs.getString("total_marks");
                return new Performance(this.studentId, courseName, obtainMarks, maximumMarks, totalMarks); // Return the Performance object
            } else {
                return null; // No performance record found with the given student ID
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error while retrieving performance details.");
            e.printStackTrace();
            return null;
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close(); // Close ResultSet
                if (stm != null) stm.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input performance details
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Obtained Marks: ");
        String obtainMarks = scanner.nextLine();
        System.out.print("Enter Maximum Marks: ");
        String maximumMarks = scanner.nextLine();
        System.out.print("Enter Total Marks: ");
        String totalMarks = scanner.nextLine();

        // Create a new Performance object
        Performance performance = new Performance(studentId, courseName, obtainMarks, maximumMarks, totalMarks);

        // Attempt to add the performance record to the database
        if (performance.addPerformance()) {
            System.out.println("Performance record added successfully!");
        } else {
            System.out.println("Failed to add performance record.");
        }

        // Update performance record
        System.out.print("Do you want to update the performance record? (yes/no): ");
        String updateResponse = scanner.nextLine();
        if (updateResponse.equalsIgnoreCase("yes")) {
            System.out.print("Enter new Course Name: ");
            performance.setCourseName(scanner.nextLine());
            System.out.print("Enter new Obtained Marks: ");
            performance.setObtainMarks(scanner.nextLine());
            System.out.print("Enter new Maximum Marks: ");
            performance.setMaximumMarks(scanner.nextLine());
            System.out.print("Enter new Total Marks: ");
            performance.setTotalMarks(scanner.nextLine());

            if (performance.updatePerformance()) {
                System.out.println("Performance record updated successfully!");
            } else {
                System.out.println("Failed to update performance record.");
            }
        }

        // View performance record
        System.out.print("Do you want to view the performance record? (yes/no): ");
        String viewResponse = scanner.nextLine();
        if (viewResponse.equalsIgnoreCase("yes")) {
            Performance retrievedPerformance = performance.viewPerformanceDetails();
            if (retrievedPerformance != null) {
                System.out.println("Performance Details:");
                System.out.println("Student ID: " + retrievedPerformance.getStudentId());
                System.out.println("Course Name: " + retrievedPerformance.getCourseName());
                System.out.println("Obtained Marks: " + retrievedPerformance.getObtainMarks());
                System.out.println("Maximum Marks: " + retrievedPerformance.getMaximumMarks());
                System.out.println("Total Marks: " + retrievedPerformance.getTotalMarks());
            } else {
                System.out.println("No performance record found for the given Student ID.");
            }
        }

        scanner.close();
    }
}
