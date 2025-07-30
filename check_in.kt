import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun parseDateTime(input: String?): LocalDateTime? {
    return try {
        if (input.isNullOrBlank()) null
        else LocalDateTime.parse(input)  // Uses ISO_LOCAL_DATE_TIME format
    } catch (_: DateTimeParseException) {
        println("Invalid date-time format. Expected: yyyy-MM-ddTHH:mm (e.g., 2025-07-29T09:30)")
        null
    }
}

data class DataEmployee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val jobRole: String,
    val managerId: String
)

data class DataAttendance(
    val employeeId: Int,
    val dateTimeOfCheckIn: LocalDateTime
)

class Employee{
    private val employeeList = mutableListOf<DataEmployee>()
    private var id = 1001  // Initial employee ID

    fun addEmployee(
        firstName: String,
        lastName: String,
        jobRole: String,
        managerId: String
    ): Int {
        val newEmployee = DataEmployee(id, firstName, lastName, jobRole, managerId)
        employeeList.add(newEmployee)
        val assignedid = id
        id++
        return assignedid

    }

    fun getAllEmployees(): List<DataEmployee> {
        return employeeList
    }

    fun exists(id: Int): Boolean {
        return employeeList.any { it.id == id }
    }

}

class Attendance{
    val checkInList = mutableListOf<DataAttendance>()

    fun hasAlreadyCheckedIn(employeeId: Int, date: LocalDate): Boolean {
        return checkInList.any {
            it.employeeId == employeeId && it.dateTimeOfCheckIn.toLocalDate() == date
        }
    }

    fun addCheckIn(employeeId: Int, dateTime: LocalDateTime) {
        checkInList.add(DataAttendance(employeeId, dateTime))
    }

    fun getAllCheckedInEmployees(): List<DataAttendance> {
        return checkInList
    }
}

fun main() {
    val empManager = Employee()
    val attendanceManager = Attendance()

    while (true) {
        println("\n==== Employee Attendance System ====")
        println("1. Add Employee")
        println("2. View All Employees")
        println("3. View All CheckedIn-Employees")
        println("4. Check In")
        println("5. Exit")
        print("Enter your choice: ")
        when (readlnOrNull()?.trim()) {

            "1" -> {
                print("Enter First Name: ")
                val firstName = readlnOrNull()?.trim().orEmpty()

                print("Enter Last Name: ")
                val lastName = readlnOrNull()?.trim().orEmpty()

                print("Enter Job Role: ")
                val role = readlnOrNull()?.trim().orEmpty()

                print("Enter Manager ID: ")
                val managerId = readlnOrNull()
                if (managerId == null) {
                    println(" Invalid Manager ID.")
                    continue
                }

                val assignedid = empManager.addEmployee( firstName, lastName, role, managerId)
                println("Employee $firstName added successfully with Id : $assignedid ")
            }

            "2" -> {
                val employees = empManager.getAllEmployees()
                if (employees.isEmpty()) {
                    println(" No employees found.")
                } else {
                    println(" Employee List:")
                    employees.forEach {
                        println(" ID: ${it.id}, Name: ${it.firstName} ${it.lastName}, Role: ${it.jobRole}, Manager ID: ${it.managerId}")
                    }
                }
            }

            "3" -> {
                val checkIns = attendanceManager.getAllCheckedInEmployees()
                if (checkIns.isEmpty()) {
                    println(" No Employees checked in.")
                } else {
                    println(" Check-In Records:")
                    checkIns.forEach {
                        println(" ID: ${it.employeeId}, DateTime: ${it.dateTimeOfCheckIn}")
                    }
                }
            }

            "4" -> {
                print("Enter Employee ID for Check-In: ")
                val id = readlnOrNull()?.toIntOrNull()
                if (id == null || !empManager.exists(id)) {
                    println(" Employee Id not found.")
                    continue
                }

                print("Enter DateTime (yyyy-MM-ddTHH:mm) or press Enter for currentDateTime: ")
                val dateTimeInput = readlnOrNull()
                val parsedDateTime = parseDateTime(dateTimeInput) ?: LocalDateTime.now()
                if (parsedDateTime.isAfter(LocalDateTime.now())) {
                    println(" Date-time cannot be in the future.")
                }else {
                    if (attendanceManager.hasAlreadyCheckedIn(id, parsedDateTime.toLocalDate())) {
                        println("Employee $id already checked in today.")
                    } else {
                        attendanceManager.addCheckIn(id, parsedDateTime)
                        println("Employee $id Check-in successful at $parsedDateTime")
                    }
                }}

            "5" -> {
                println("Exiting...")
                break
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}

