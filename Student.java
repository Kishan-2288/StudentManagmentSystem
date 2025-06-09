package StudentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;

public class Student {

    private String studentId;
    private String name;
    private String email;
    private String phone;
    private String Password;
    

    // Database credentials - change as per your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Jonsir@12345";

    public Student(String studentId, String name, String email, String phone,String Password) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.Password = Password;
       
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
     public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
  
    /**
     * Adds the student to the database.
     * @return true if insert was successful, false otherwise
     */
    public boolean addStudent() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "INSERT INTO student (student_id, name, email, phone,Password) VALUES (?, ?, ?, ?, ?)";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.studentId);
            stm.setString(2, this.name);
            stm.setString(3, this.email);
            stm.setString(4, this.phone);
            stm.setString(5, this.Password);
           

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while adding student.");
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

    public boolean updateStudent() {
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "UPDATE student SET name = ?, email = ?, phone = ?, Password = ? WHERE student_id = ?";

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, this.name); // Set the new name
            stm.setString(2, this.email); // Set the new email
            stm.setString(3, this.phone);  // Set the new phone
            stm.setString(4, this.Password);
            stm.setString(5, this.studentId); // Set the student ID for the WHERE clause

            int rowsUpdated = stm.executeUpdate(); // Execute the update
            return rowsUpdated > 0; // Return true if at least one row was updated

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while updating student.");
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

    public Student viewStudentDetails() {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; // Declare ResultSet here
        String sql = "SELECT name, email, phone,Password FROM student WHERE student_id = ?";

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
                // Create a new Student object with the retrieved details
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String Password = rs.getString("Password");
                return new Student(this.studentId, name, email,phone,Password); // Return the Student object
            } else {
                return null; // No student found with the given ID
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error while retrieving student details.");
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

        // Input student details
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
         System.out.print("Enter Password: ");
        String Password = scanner.nextLine();

        // Create a new Student object
        Student student = new Student(studentId, name, email,phone,Password);

        // Attempt to add the student to the database
        if (student.addStudent()) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }

        scanner.close();
    }
}
