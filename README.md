# StudentManagmentSystem

# College Management System

This Java-based application provides an administrative interface for managing students, courses, and fee information. The system allows an admin to perform CRUD (Create, Read, Update, Delete) operations on student and course data and manage login access.

## 💻 Features

- **Admin Login Page**
  - Secure login for administrators
- **Student Management**
  - Add, view, and manage student records
- **Course Management**
  - Manage available courses
- **Fees Management**
  - View and manage student fee details
- **Database Connectivity**
  - Uses JDBC to connect with a MySQL database

## 🗂️ Project Structure

```
src/
├── AdminHomepage.java      // Main admin dashboard
├── loginPage.java          // Admin login interface
├── Student.java            // Student management module
├── Course.java             // Course management module
├── Fees.java               // Fees management module
├── ConnectionClass.java    // Handles DB connection
```

## 🛠️ Technologies Used

- Java (Swing for GUI)
- JDBC
- MySQL

## 🧾 Prerequisites

- Java JDK 8 or above
- MySQL Server
- MySQL JDBC Driver (add to classpath)
- IDE like Eclipse, IntelliJ, or NetBeans

## 🛠️ Setup Instructions

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/college-management-system.git
   cd college-management-system
   ```

2. **Set Up Database**

   - Create a MySQL database named `college_db` (or as used in `ConnectionClass.java`)
   - Run the provided SQL scripts (if any) to create the necessary tables (`students`, `courses`, `fees`, `admin`, etc.)

3. **Configure Database Connection**

   - Update the database credentials in `ConnectionClass.java`:

     ```java
     String username = "root";
     String password = "your_password";
     String url = "jdbc:mysql://localhost:3306/college_db";
     ```

4. **Run the Application**

   - Compile all `.java` files
   - Run `loginPage.java` to start the app

## 🔐 Default Admin Credentials

> ⚠️ Make sure to change the credentials after first use.

- **Username:** admin  
- **Password:** admin123



## 📜 License

This project is for educational purposes. You can modify and use it freely with attribution.
