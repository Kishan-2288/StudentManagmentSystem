🎓 Student Management System – Java Project

This project is a Java-based Student Management System that provides complete functionality for managing student data through a graphical user interface. It handles user authentication, course enrollment, academic performance, fee tracking, and database operations using JDBC.

The system supports both Admin and Student roles:

Admin can manage students, courses, and fees.

Student can log in to view personal academic and financial information.

📁 Project Structure

.
├── AdminHomepage.java         # Admin dashboard for managing students, performance, and fees
├── StudentHomepage.java       # Student dashboard with personal academic and fee information
├── Student.java               # Data model for student details
├── Course.java                # Handles course details and structure
├── Performance.java           # Stores and manages student academic performance
├── Fees.java                  # Manages fee payment status and related logic
├── loginPage.java             # Entry point with login interface
├── ConnectionClass.java       # MySQL database connection configuration

✅ Features

🔐 Login Authentication

Role-based access for Admin and Student

Validation using username-password combinations stored in database

👨‍🎓 Student Panel

View courses enrolled

Check academic performance (grades)

See fee dues and payment history

🛠️ Admin Panel

Add, update, and delete student data

Assign or update course enrollments

View and update student performance

Track fee payment status

🛠️ Technologies Used

Java (Core + Swing for GUI)

MySQL (Relational Database)

JDBC API (Java Database Connectivity)

Object-Oriented Programming principles

🚀 Getting Started

Prerequisites

Java JDK 8 or above

MySQL Server

MySQL JDBC Connector (add to classpath)

Setup Instructions

Download or clone the repository.

Set up your MySQL database with necessary tables (see below).

Update DB credentials in ConnectionClass.java:

con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "your_password");

Compile the source code:

javac *.java

Run the application:

java loginPage

🧹 Suggested MySQL Database Schema

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    course VARCHAR(50),
    password VARCHAR(100)
);

CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100),
    instructor VARCHAR(100)
);

CREATE TABLE performance (
    student_id INT,
    subject VARCHAR(100),
    grade CHAR(2),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE fees (
    student_id INT,
    amount_due DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

💼 Example Workflow

Admin logs in and adds a new student.

Admin assigns courses and sets performance records.

Student logs in to check academic and fee status.

Admin updates fee records upon payment.


📄 License

This project is open-source and available for educational or academic use only.

ℹ️ Additional Notes

Make sure MySQL server is running when you execute the program.

Tables must be created in the database before running the application.

Update credentials before compiling to avoid SQLException.

