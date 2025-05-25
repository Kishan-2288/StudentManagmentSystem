package StudentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Course {

    private String courseId;
    private String courseName;
    private int credits; // Changed to int

    // Database credentials - change as per your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Jonsir@12345";

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters and setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Adds the course to the database.
     * @return true if insert was successful, false otherwise
     */
    public boolean addCourse() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "INSERT INTO courses (course_id, course_name, credits) VALUES (?, ?, ?)"; // Fixed SQL

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.courseId);
            stm.setString(2, this.courseName);
            stm.setInt(3, this.credits); // Changed to setInt

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while adding course.");
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
     * Updates the course in the database.
     * @return true if update was successful, false otherwise
     */
    public boolean updateCourse() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "UPDATE courses SET course_name = ?, credits = ? WHERE course_id = ?"; // Fixed SQL

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.courseName);
            stm.setInt(2, this.credits); // Changed to setInt
            stm.setString(3, this.courseId); // Corrected order

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating course.");
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
     * Views course details from the database by course ID.
     * @return Course object if found, null otherwise
     */
    public Course viewCourseDetails() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; // Declare ResultSet here
        String sql = "SELECT course_name, credits FROM courses WHERE course_id = ?";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.courseId); // Set the course ID for the WHERE clause

            // Execute query
            rs = stm.executeQuery();

            // Check if a result was returned
            if (rs.next()) {
                // Create a new Course object with the retrieved details
                String name = rs.getString("course_name");
                int credits = rs.getInt("credits"); // Changed to getInt
                return new Course(this.courseId, name, credits); // Return the Course object
            } else {
                return null; // No course found with the given ID
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error while retrieving course details.");
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

        // Input course details
        System.out.print("Enter Course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Credits: ");
        
        int credits = 0;
        while (true) {
            try {
                credits = Integer.parseInt(scanner.nextLine()); // Handle input properly
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer for credits: ");
            }
        }

        // Create a new Course object
        Course course = new Course(courseId, courseName, credits);

        // Attempt to add the course to the database
        if (course.addCourse()) {
            System.out.println("Course added successfully!");
        } else {
            System.out.println("Failed to add course.");
        }

        // Optionally, you can add more functionality to view or update courses here

        scanner.close();
    }
}
