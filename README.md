# PieQ_Attendance_cli

## 📌 Overview

This project is a Kotlin-based CLI (Command Line Interface) application that allows employees to **check in** using their ID and date-time. It validates input, prevents duplicate check-ins per day, and provides feedback to the user.

---

## 👥 Employee Data

We use a data class called `Employee` with the following fields:

- `employeeId`: Unique ID for the employee  
- `firstName`: First name of the employee  
- `lastName`: Last name of the employee  
- `jobRole`: Employee's job title  
- `managerId`: Manager's ID  

All employees are stored in a mutable list named `employeeList`.

---

## 📅 Attendance Data

We use a data class called `Attendance` with the following fields:

- `employeeId`: ID of the employee who checked in  
- `dateTimeOfCheckIn`: The exact date and time of check-in  

All check-in records are stored in a mutable list named `checkInList`.

---

## ✅ Core Functions

### 🔹 checkIn(id: Int, dateTimeInput: String?)

- Parses the date-time input (or uses current time).
- Validates the employee ID.
- Checks whether the employee has already checked in on the same date.
- If valid, saves the check-in record to `checkInList`.
- Displays a confirmation message.

---

### 🔹 parseDateTimeOrNow(input: String?): LocalDateTime?

- If the input is blank, uses `LocalDateTime.now()`.
- If input is provided, parses it using the pattern `yyyy-MM-dd HH:mm`.
- If the input date-time is in the future, it is rejected.
- Invalid formats are handled with an error message.

Example error:  
`Invalid format. Use YYYY-MM-DD HH:MM (e.g., 2025-07-29 09:30)`

---

### 🔹 validateCheckIn(id: Int, dateTime: LocalDateTime): Employee?

- Checks if the employee exists in `employeeList`.
- Verifies if the employee already checked in on the given date.
- Returns the employee object if validation passes.
- otherwise null
