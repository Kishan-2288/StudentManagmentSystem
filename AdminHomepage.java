package StudentManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AdminHomepage extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Jonsir@12345";

    JLabel backgroundLabel;
    Font menuFont, menuItemFont;
    String account2;

    // Top menu buttons
    JButton teacherProfileButton, studentProfileButton, courseDetailsButton,
            feesDetailsButton, performanceButton, resultButton, logoutButton;

    // Popup menus for each top menu button
    JPopupMenu studentProfileMenu, courseDetailsMenu, feesDetailsMenu,
            performanceMenu, resultMenu;

    public AdminHomepage(String account) {
        this.account2 = account;
        setTitle("Admin Login Page");
        setSize(1280, 780);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        menuFont = new Font("Arial", Font.BOLD, 16);
        menuItemFont = new Font("Arial", Font.PLAIN, 14);

        // Load background image
        ImageIcon backgroundImage = new ImageIcon(
                ClassLoader.getSystemResource("StudentManagement/Images/Main2.jpg"));
        Image scaledImage = backgroundImage.getImage().getScaledInstance(1280, 780, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 1280, 780);
        backgroundLabel.setLayout(null);
        add(backgroundLabel);

        // Create top menu buttons (styled)
        studentProfileButton = createMenuButton("Student Profile", 400, 380);
        courseDetailsButton = createMenuButton("Course Details", 600, 380);
        feesDetailsButton = createMenuButton("Fees Details", 800, 380);
        performanceButton = createMenuButton("Performance", 400, 440);
        resultButton = createMenuButton("Result", 600, 440);
        logoutButton = createMenuButton("Logout", 800, 440);

        // Add buttons to background label
        backgroundLabel.add(studentProfileButton);
        backgroundLabel.add(courseDetailsButton);
        backgroundLabel.add(feesDetailsButton);
        backgroundLabel.add(performanceButton);
        backgroundLabel.add(resultButton);
        backgroundLabel.add(logoutButton);

        // Create popup menus
        studentProfileMenu = createPopupMenu(new String[]{
                "Add Student Details", "Update Student Details", "View Student Details"});
        courseDetailsMenu = createPopupMenu(new String[]{
                "Add Course Details", "Update Course Details", "View Course Details"});
        feesDetailsMenu = createPopupMenu(new String[]{
                "Add Fees Details", "Update Fees Details", "View Fees Details"});
        performanceMenu = createPopupMenu(new String[]{
                "Add Performance Details", "Update Performance Details", "View Performance Details"});
        resultMenu = createPopupMenu(new String[]{
                "Add Result Details", "Update Result Details"});

        // Add action listeners to buttons
        studentProfileButton.addActionListener(e -> studentProfileMenu.show(studentProfileButton, 0, studentProfileButton.getHeight()));
        courseDetailsButton.addActionListener(e -> courseDetailsMenu.show(courseDetailsButton, 0, courseDetailsButton.getHeight()));
        feesDetailsButton.addActionListener(e -> feesDetailsMenu.show(feesDetailsButton, 0, feesDetailsButton.getHeight()));
        performanceButton.addActionListener(e -> performanceMenu.show(performanceButton, 0, performanceButton.getHeight()));
        resultButton.addActionListener(e -> resultMenu.show(resultButton, 0, resultButton.getHeight()));

        // Logout button action
        logoutButton.addActionListener(e -> {
            new loginPage().setVisible(true);
            dispose();
        });
    }

    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 30);
        button.setFont(menuFont);
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        return button;
    }

    private JPopupMenu createPopupMenu(String[] menuItems) {
        JPopupMenu popupMenu = new JPopupMenu();
        for (String item : menuItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setFont(menuItemFont);
            menuItem.addActionListener(e -> handleMenuAction(item));
            popupMenu.add(menuItem);
        }
        return popupMenu;
    }

    private void handleMenuAction(String command) {
        switch (command.trim()) {
            case "Add Student Details":
                addStudentDetails();
                break;
            case "Update Student Details":
                updateStudentDetails();
                break;
            case "View Student Details":
                viewStudentDetails();
                break;
            case "Add Course Details":
                addCourse();
                break; // Added break statement
             case "Update Course Details":
                updateCourseDetails();
                break;  
            case "View Course Details":
                viewCourseDetails();
                break; 
            case "Add Fees Details":
                addFees();
                break; // Added break statement
             case "Update Fees Details":
                updateFeesDetails();
                break;  
            case "View Fees Details":
                viewFeesDetails();
                break;     
            default:
                System.out.println("Unknown action: " + command);
        }
    }

    private void addStudentDetails() {
        String studentId = JOptionPane.showInputDialog("Enter Student ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String phone = JOptionPane.showInputDialog("Enter Phone:");

        if (studentId != null && name != null && email != null && phone != null) {
            Student student = new Student(studentId, name, email, phone);
            if (student.addStudent()) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student.");
            }
        }
    }

    private void updateStudentDetails() {
        String studentId = JOptionPane.showInputDialog("Enter Student ID to Update:");

        if (studentId != null) {
           
            String name = JOptionPane.showInputDialog("Enter New Name:");
            String email = JOptionPane.showInputDialog("Enter New Email:");
            String phone = JOptionPane.showInputDialog("Enter New Phone:");

            if (name != null && email != null && phone != null) {
                
                Student student = new Student(studentId, name, email, phone);
                if (student.updateStudent()) { // Call the correct update method
                    JOptionPane.showMessageDialog(this, "Student updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update student. Please check if the student ID exists.");
                }
            }
        }
    }

    private void viewStudentDetails() {
        String studentId = JOptionPane.showInputDialog("Enter Student ID to View:");

        if (studentId != null) {
          
            Student student = new Student(studentId, null, null, null); // Only ID is needed for retrieval
            Student retrievedStudent = student.viewStudentDetails(); // Call the view method

            if (retrievedStudent != null) {
                // Display the student details
                String message = "Student ID: " + retrievedStudent.getStudentId() +
                        "\nName: " + retrievedStudent.getName() +
                        "\nEmail: " + retrievedStudent.getEmail() +
                        "\nPhone: " + retrievedStudent.getPhone();
                JOptionPane.showMessageDialog(this, message, "Student Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

   private void addCourse() {
    String courseId = JOptionPane.showInputDialog("Enter Course ID:");
    String courseName = JOptionPane.showInputDialog("Enter Course Name:");
    String creditsInput = JOptionPane.showInputDialog("Enter Credits:");

    if (courseId != null && courseName != null && creditsInput != null) {
        try {
            int credits = Integer.parseInt(creditsInput); // Convert String to int
            Course course = new Course(courseId, courseName, credits); // Corrected constructor call
            if (course.addCourse()) {
                JOptionPane.showMessageDialog(this, "Course added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add Course.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for credits. Please enter a valid integer.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Input was cancelled or invalid.");
    }
}
   
    private void updateCourseDetails() {
    String courseId = JOptionPane.showInputDialog("Enter Course ID to Update:");

    if (courseId != null) {
        String courseName = JOptionPane.showInputDialog("Enter New Course Name:");
        String creditsInput = JOptionPane.showInputDialog("Enter New Credits:");

        if (courseName != null && creditsInput != null) {
            try {
                int credits = Integer.parseInt(creditsInput); // Convert String to int
                Course course = new Course(courseId, courseName, credits); // Create Course object with new details
                if (course.updateCourse()) { // Call the correct update method
                    JOptionPane.showMessageDialog(this, "Course updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update Course. Please check if the course ID exists.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for credits. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Input was cancelled or invalid.");
        }
    }
}
    
    private void viewCourseDetails() {
    String courseId = JOptionPane.showInputDialog("Enter Course ID to View:");

    if (courseId != null) {
        Course course = new Course(courseId, null, 0); // Only ID is needed for retrieval
        Course retrievedCourse = course.viewCourseDetails(); // Call the view method

        if (retrievedCourse != null) {
            // Display the course details
            String message = "Course ID: " + retrievedCourse.getCourseId() +
                    "\nCourse Name: " + retrievedCourse.getCourseName() +
                    "\nCredits: " + retrievedCourse.getCredits();
            JOptionPane.showMessageDialog(this, message, "Course Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No course found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void addFees() {
    String id = JOptionPane.showInputDialog("Enter  ID:");
    String ProgrameName = JOptionPane.showInputDialog("Enter Programe Name:");
    String FeesInput = JOptionPane.showInputDialog("Enter Fees:");

    if (id != null && ProgrameName != null && FeesInput != null) {
        try {
            int Fees = Integer.parseInt(FeesInput); // Convert String to int
            Fees fees = new Fees(id, ProgrameName, Fees); // Corrected constructor call
            if (fees.addFees()) {
                JOptionPane.showMessageDialog(this, "Fees added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add Fees.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for Fees. Please enter a valid integer.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Input was cancelled or invalid.");
    }
}
   
    private void updateFeesDetails() {
    String id = JOptionPane.showInputDialog("Enter  ID to Update:");

    if (id != null) {
        String ProgrameName = JOptionPane.showInputDialog("Enter New Programe Name:");
        String FeesInput = JOptionPane.showInputDialog("Enter New Fees:");

        if (ProgrameName != null && FeesInput != null) {
            try {
                int Fees = Integer.parseInt(FeesInput); // Convert String to int
                Fees fees = new Fees(id, ProgrameName, Fees); // Create Course object with new details
                if (fees.updateFees()) { // Call the correct update method
                    JOptionPane.showMessageDialog(this, "Programe updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update Programe. Please check if the course ID exists.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for Programe. Please enter a valid integer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Input was cancelled or invalid.");
        }
    }
}
    
   private void viewFeesDetails() {
    String id = JOptionPane.showInputDialog("Enter  ID to View:");

    if (id != null && !id.trim().isEmpty()) {
        Fees fees = new Fees(id, null, 0); // Only ID is needed for retrieval
        Fees retrievedFees = fees.viewFeesDetails(); // Call the view method

        if (retrievedFees != null) {
            // Display the fees details
            String message = "Program ID: " + retrievedFees.getProgramId() +
                    "\nProgram Name: " + retrievedFees.getProgramName() +
                    "\nFees: " + retrievedFees.getFees();
            JOptionPane.showMessageDialog(this, message, "Fees Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No fees found with the given Program ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Program ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminHomepage adminHomepage = new AdminHomepage("Admin");
            adminHomepage.setVisible(true);
        });
    }
}

