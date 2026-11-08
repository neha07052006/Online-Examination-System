
# Online Examination System

## Project Description
The Online Examination System is a Java-based application developed using Java Swing and MySQL. It allows students to log in, update their profiles, attend online MCQ examinations, and view their results.

## Technologies Used
- Java
- Java Swing
- JDBC
- MySQL
- VS Code

## Features
1. Student Login
2. Student Profile Update
3. Multiple Choice Questions (MCQ)
4. Next and Previous Question Navigation
5. 30-Minute Countdown Timer
6. Automatic Exam Submission
7. Manual Exam Submission
8. Result Calculation
9. Correct and Incorrect Answer Details
10. Logout and Exit Confirmation

## Database
Database Name: online_exam_db

Tables:
- Students
- questions
- results

## How to Run
1. Install Java JDK.
2. Install MySQL.
3. Add MySQL Connector JAR to the lib folder.
4. Configure database credentials in DBConnection.java.
5. Open the project in VS Code.
6. Compile the Java files:

```powershell
javac -cp ".\src;.\lib\*" (Get-ChildItem .\src -Filter *.java | ForEach-Object { $_.FullName })
```

7. Run the application:

```powershell
java -cp ".\src;.\lib\*" LoginFrame
```

## Project Type
OASIS INFOBYTE Java Development Internship - Task 4
