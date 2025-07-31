import service.Employee
import service.EmployeeList
import service.Attendance
import data.Manager
import data.Role
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import java.time.format.DateTimeFormatter

fun main() {
    val employeeService = Employee(EmployeeList())
    val attendanceService = Attendance()

    while (true) {
        println("\n==== Employee Management Menu ====")
        println("1. Add Employee")
        println("2. List Employees")
        println("3. Check-In")
        println("4. Check-Out")
        println("5. View Working Hours")
        println("6. View Check-In List")
        println("7. Exit")
        print("Enter your choice: ")

        when (readln().toIntOrNull()) {
            1 -> handleAddEmployee(employeeService)
            2 -> handleListEmployees(employeeService)
            3 -> handleCheckIn(attendanceService)
            4 -> handleCheckOut(attendanceService)
            5 -> handleViewWorkingHours(attendanceService)
            6 -> handleViewCheckInList(attendanceService)
            7 -> {
                println("Exiting application.")
                return
            }
            else -> println("Invalid choice. Please enter a number from 1 to 7.")
        }
    }
}

fun handleAddEmployee(empService: Employee) {
    print("Enter First Name: ")
    val firstName = readln().trim()
    print("Enter Last Name: ")
    val lastName = readln().trim()

    println("Select Role:")
    Role.entries.forEachIndexed { index, role -> println("${index + 1}. $role") }
    print("Enter role number: ")
    val role = readln().toIntOrNull()?.let { Role.entries.getOrNull(it - 1) }

    if (role == null) {
        println("Invalid role.")
        return
    }

    val reportingManager = if (role != Role.MANAGER) {
        Manager.printAll()
        print("Enter Manager ID: ")
        val managerId = readln().trim()
        Manager.fromId(managerId)?.also {
            println("Reporting to: ${it.fullName}")
        } ?: run {
            println("Invalid Manager ID.")
            return
        }
    } else null

    empService.addEmployee(firstName, lastName, role, reportingManager)?.let {
        println("Entry Success. Generated ID: $it")
    }
}

fun handleListEmployees(empService: Employee) {
    val employees = empService.getAllEmployees()
    if (employees.isEmpty()) {
        println("No employees found.")
    } else {
        println("\nEmployee List:")
        employees.forEach {
            val mgr = it.reportingTo?.let { m -> " | Manager: ${m.fullName}" } ?: ""
            println("${it.id} | ${it.firstName} ${it.lastName} | ${it.role}$mgr")
        }
    }
}

fun handleCheckIn(attendanceService: Attendance) {
    print("Enter Employee ID: ")
    val empId = readln().trim()
    if (empId.isBlank()) {
        println("Invalid Employee ID.")
        return
    }

    print("Enter Check-In DateTime (yyyy-MM-ddTHH:mm) or press Enter for current time: ")
    val input = readlnOrNull()
    val dateTime = parseDateTime(input) ?: LocalDateTime.now()

    if (dateTime.isAfter(LocalDateTime.now())) {
        println("Date-time cannot be in the future.")
        return
    }

    if (attendanceService.checkIn(empId, dateTime)) {
        val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm.ss"))
        println("Check-in successful at $formattedTime")
    }
}

fun handleCheckOut(attendanceService: Attendance) {
    print("Enter Employee ID: ")
    val empId = readln().trim()
    if (empId.isBlank()) {
        println("Invalid Employee ID.")
        return
    }

    print("Enter Check-Out DateTime (yyyy-MM-ddTHH:mm) or press Enter for current time: ")
    val input = readlnOrNull()
    val dateTime = parseDateTime(input) ?: LocalDateTime.now()

    if (dateTime.isAfter(LocalDateTime.now())) {
        println("Date-time cannot be in the future.")
        return
    }

    if (attendanceService.checkOut(empId, dateTime)) {
        val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm.ss"))
        println("Check-out successful at $formattedTime")
    }
}

fun handleViewWorkingHours(attendanceService: Attendance) {
    print("Enter Employee ID: ")
    val empId = readln().trim()
    if (empId.isBlank()) {
        println("Invalid Employee ID.")
        return
    }

    print("Enter Date (yyyy-MM-dd): ")
    val dateInput = readln()
    val date = try {
        LocalDate.parse(dateInput)
    } catch (_: Exception) {
        println("Invalid date format.")
        return
    }

    println(attendanceService.getWorkDuration(empId, date))
}

fun handleViewCheckInList(attendanceService: Attendance) {
    val list = attendanceService.getAttendanceRecords()
    if (list.isEmpty()) {
        println("No one is currently checked in.")
    } else {
        println("\nCurrently Checked-In Employees:")
        list.forEach {
            println("EmpID: ${it.employeeId} | Check-In: ${it.dateTimeOfCheckIn}")
        }
    }
}

fun parseDateTime(input: String?): LocalDateTime? {
    return try {
        if (input.isNullOrBlank()) null
        else LocalDateTime.parse(input.trim())
    } catch (_: DateTimeParseException) {
        println("Invalid date-time format. Expected: yyyy-MM-ddTHH:mm")
        null
    }
}
