# 🕒 PieQ_Attendance_cli

## 📌 Overview

**PieQ_Attendance_cli** is a Kotlin-based **Command Line Interface (CLI)** application designed to manage **employee attendance** through check-in records. It ensures that:

- Employees can check in by entering their ID and date-time.
- Duplicate check-ins (on the same day) are prevented.
- Input is validated with clear feedback messages.

---

## 👥 Employee Data

We use a data class called `DataEmployee` with the following fields:

- `id`: Unique identifier for each employee (auto-incremented)
- `firstName`: First name of the employee
- `lastName`: Last name of the employee
- `jobRole`: Employee's job title
- `managerId`: Alphanumeric ID of the employee's manager (e.g., MG100, A123)



Employees are stored in a list named `employeeList` (internal to the `Employee` class).

### Functions in `Employee`:

- `addEmployee(firstName, lastName, jobRole, managerId)`: Adds a new employee.
- `getAllEmployees()`: Returns a list of all registered employees.
- `employeeExists(id)`: Returns `true` if an employee with the given ID exists.

---

## 📅 Attendance Data

We use a data class called `DataAttendance` with the following fields:

- `employeeId`: ID of the employee who checked in
- `dateTimeOfCheckIn`: The exact date and time of check-in (as `LocalDateTime`)

Check-in records are stored in `checkInList` (inside the `Attendance` class).

### Functions in `Attendance`:

- `addCheckedInEmployees(employeeId, dateTime)`: Adds a new check-in entry.
- `getAllCheckedInEmployees()`: Returns all check-in records.
- `hasAlreadyCheckedIn(employeeId, date)`: Prevents multiple check-ins on the same day.

---

## 🧠 Core Logic Functions

### 🔹 `parseDateTime(input: String?): LocalDateTime?`

- Parses user input in ISO format: `yyyy-MM-ddTHH:mm`.
- If the input is blank, returns `null`.
- If parsing fails, an error message is shown.


