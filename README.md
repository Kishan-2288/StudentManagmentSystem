🎓 Student Management System – Java Project
This project is a Java-based Student Management System that provides functionality for managing student data, including login authentication, course details, performance tracking, and fee status. The system supports both Admin and Student interfaces.

📁 Project Structure
graphql
Copy
Edit


 
├── AdminHomepage.java         # Admin dashboard for managing students and data
├── StudentHomepage.java       # Student dashboard to view courses, performance, fees
├── Student.java               # Class for storing and managing student information
├── Course.java                # Manages course-related data
├── Performance.java           # Tracks student academic performance
├── Fees.java                  # Handles student fee details and payments
├── loginPage.java             # Login GUI for authentication (Student/Admin)
├── ConnectionClass.java       # Establishes JDBC connection with MySQL database


✅ Features

🔐 Authentication
Login page for Admin and Student

Validation via username and password

👨‍🎓 Student Interface
View enrolled courses

Track academic performance

View fee status and history

🛠️ Admin Interface
Add, delete, or update student records

View complete student academic and fee details

Manage course enrollments

🛠️ Technologies Used
Java Swing for GUI design

MySQL for database management

JDBC for database connectivity

Object-Oriented Programming (OOP) principles

🚀 Getting Started
Prerequisites
Java JDK 8 or higher

MySQL Server installed and running

MySQL JDBC Driver (mysql-connector-java.jar)

Setup Instructions
Clone or download this repository.

Configure MySQL database using the following credentials (can be updated in ConnectionClass.java):

java
Copy
Edit
con = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "your_password");
Compile all .java files:

bash
Copy
Edit
javac *.java
Run the application:

bash
Copy
Edit
java loginPage
🧩 Database Structure (Suggested)
You should have the following tables:

students(id, name, course, password, ...)

courses(id, course_name, instructor, ...)

fees(student_id, amount_due, status)

performance(student_id, subject, grade)

