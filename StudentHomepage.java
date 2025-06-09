
package StudentManagement;


import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;

public class StudentHomepage extends JFrame {
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

    public StudentHomepage(String account) {
        this.account2 = account;
        setTitle("Student Login Page");
        setSize(1280, 780);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        menuFont = new Font("Arial", Font.BOLD, 16);
        menuItemFont = new Font("Arial", Font.PLAIN, 14);

        // Load background image
        ImageIcon backgroundImage = new ImageIcon(
                ClassLoader.getSystemResource("StudentManagement/Images/StudentDesh.jpg"));
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
        logoutButton = createMenuButton("Logout", 800, 440);

        // Add buttons to background label
        backgroundLabel.add(studentProfileButton);
        backgroundLabel.add(courseDetailsButton);
        backgroundLabel.add(feesDetailsButton);
        backgroundLabel.add(performanceButton);
        backgroundLabel.add(logoutButton);

        // Create popup menus
        studentProfileMenu = createPopupMenu(new String[]{
                 "View Student Details"});
        courseDetailsMenu = createPopupMenu(new String[]{
                 "View Course Details"});
        feesDetailsMenu = createPopupMenu(new String[]{
                 "View Fees Details"});
        performanceMenu = createPopupMenu(new String[]{
                 "View Performance Details"});
       

        // Add action listeners to buttons
        studentProfileButton.addActionListener(e -> studentProfileMenu.show(studentProfileButton, 0, studentProfileButton.getHeight()));
        courseDetailsButton.addActionListener(e -> courseDetailsMenu.show(courseDetailsButton, 0, courseDetailsButton.getHeight()));
        feesDetailsButton.addActionListener(e -> feesDetailsMenu.show(feesDetailsButton, 0, feesDetailsButton.getHeight()));
        performanceButton.addActionListener(e -> performanceMenu.show(performanceButton, 0, performanceButton.getHeight()));
       

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
            case "View Student Details":
                viewStudentDetails();
                break;
            case "View Course Details":
                viewCourseDetails();
                break;
            case "View Fees Details":
                viewFeesDetails();
                break;
            case "View Performance Details":
                viewPerformanceDetails();
                break;
            default:
                System.out.println("Unknown action: " + command);
        }
    }

   

    private void viewStudentDetails() {
    // Use the logged-in student's ID directly
    String studentId = account2; // Assuming account2 holds the student ID

    if (studentId != null) {
        Student student = new Student(studentId, null, null, null, null); // Only ID is needed for retrieval
        Student retrievedStudent = student.viewStudentDetails(); // Call the view method

        if (retrievedStudent != null) {
            // Display the student details
            String message = "Student ID: " + retrievedStudent.getStudentId() +
                    "\nName: " + retrievedStudent.getName() +
                    "\nEmail: " + retrievedStudent.getEmail() +
                    "\nPhone: " + retrievedStudent.getPhone() +
                    "\nPassword: " + retrievedStudent.getPassword();
            JOptionPane.showMessageDialog(this, message, "Student Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No student found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void viewCourseDetails() {
    // Assuming the course details are not user-specific, you may want to adjust this logic
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

private void viewFeesDetails() {
    // Use the logged-in student's ID directly
    String id = account2; // Assuming account2 holds the student ID

    if (id != null && !id.trim().isEmpty()) {
        Fees feesObj = new Fees(id, null, 0); // Only ID is needed for retrieval
        Fees retrievedFees = feesObj.viewFeesDetails(); // Call the view method

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

private void viewPerformanceDetails() {
    // Use the logged-in student's ID directly
    String studentId = account2; // Assuming account2 holds the student ID

    if (studentId != null) {
        Performance performance = new Performance(studentId, null, null, null, null); // Only ID is needed for retrieval
        Performance retrievedPerformance = performance.viewPerformanceDetails(); 

        if (retrievedPerformance != null) {
            // Display the performance details
            String message = "Student ID: " + retrievedPerformance.getStudentId() +
                    "\nCourse Name: " + retrievedPerformance.getCourseName() +
                    "\nObtained Marks: " + retrievedPerformance.getObtainMarks() +
                    "\nMaximum Marks: " + retrievedPerformance.getMaximumMarks() +
                    "\nTotal Marks: " + retrievedPerformance.getTotalMarks();
            JOptionPane.showMessageDialog(this, message, "Performance Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No performance record found with the given Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentHomepage studentHomepage = new StudentHomepage("Student");
            studentHomepage.setVisible(true);
        });
    }
}

    

