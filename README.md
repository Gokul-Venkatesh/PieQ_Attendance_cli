# PieQ_Attendance_cli

## Overview

  This document explains the basic structure and logic for the **Check-In** part of the CLI app.

## 1. Employee Data

We created a data class called `Employee` with:

- `id`: Employee ID (Int)
- `firstName`: First name (String)
- `lastName`: Last name (String)
- `role`: Job role (String)
- `reportingTo`: Manager name (String)

All employees are stored in a list called `employeeList`.


## 2. Attendance Data

Another data class `Attendance` stores:

- `id`: Employee ID (Int)
- `date`: Date of check-in (String)
- `time`: Time of check-in (String)

All check-in records are stored in a list called `checkInList`.

---

We created two functions:

### ✅ `checkIn(id: Int)`
- Checks if the employee ID is valid.
- If valid and not already checked in today:
  - Saves the check-in date and time.
  - Prints success message.

### ✅ `validateCheckIn(id: Int)`
- Checks if the employee exists.
- Checks if the employee has already checked in today.

---

## 4. Tools Used

- **LocalDate.now()** – to get today’s date
- **LocalTime.now()** – to get the current time

---

## 5. Notes

- One check-in allowed per employee per day.
- Check-in data is stored in memory for now (not in files).
- We used `readln()` to get user input from terminal.

---






</pre>
