import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// --- Data Classes ---
data class Employee(
    val employeeId: Int,
    val firstName: String,
    val lastName: String,
    val jobRole: String,
    val managerId: Int
)

data class Attendance(
    val employeeId: Int,
    val dateTimeOfCheckIn: LocalDateTime
)

// --- Storage Lists ---
val employeeList = mutableListOf<Employee>()
val checkInList = mutableListOf<Attendance>()

// --- Perform Check-In ---
fun checkIn(id: Int, dateTimeInput: String?) {
    val dateTime = parseDateTimeOrNow(dateTimeInput) ?: return
    val employee = validateCheckIn(id, dateTime) ?: return

    checkInList.add(Attendance(id, dateTime))
    println(" Checked in: ${employee.firstName} ${employee.lastName} | Role: ${employee.jobRole} | ManagerID: ${employee.managerId}")
    println(" Check-in time: ${dateTime.format(dateTimeFormatter)}")
}

// --- Formatter ---
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

// --- Parse DateTime or Use Current ---
fun parseDateTimeOrNow(input: String?): LocalDateTime? {
    return if (input.isNullOrBlank()) {
        LocalDateTime.now()
    } else {
        try {
            val parsed = LocalDateTime.parse(input, dateTimeFormatter)
            if (parsed.isAfter(LocalDateTime.now())) {
                println(" Date-time cannot be in the future.")
                null
            } else {
                parsed
            }
        } catch (e: DateTimeParseException) {
            println(" Invalid format. Use YYYY-MM-DD HH:MM (e.g., 2025-07-29 09:30)")
            null
        }
    }
}

// --- Validate Check-In ---
fun validateCheckIn(id: Int, dateTime: LocalDateTime): Employee? {
    val employee = employeeList.find { it.employeeId == id }

    if (employee == null) {
        println(" Employee with ID $id not found.")
        return null
    }

    val alreadyCheckedIn = checkInList.any {
        it.employeeId == id && it.dateTimeOfCheckIn.toLocalDate() == dateTime.toLocalDate()
    }

    if (alreadyCheckedIn) {
        println(" Already checked in on ${dateTime.toLocalDate()}.")
        return null
    }

    return employee
}

// --- Main Function ---
fun main() {
    // Sample Employees
    employeeList.add(Employee(1, "Alice", "Johnson", "Developer", 121))
    employeeList.add(Employee(2, "Tom", "Clark", "Tester", 121))
    employeeList.add(Employee(3, "Sara", "Williams", "Project Manager", 100))
    employeeList.add(Employee(4, "David", "Lee", "Designer", 403))
    employeeList.add(Employee(5, "Emma", "Davis", "Developer", 121))

    while (true) {
        println("\n==== Employee Attendance System ====")
        println("1. Check In")
        println("2. Exit")
        print("Enter your choice: ")
        val choice = readlnOrNull()

        when (choice) {
            "1" -> {
                print("Enter Employee ID: ")
                val idInput = readlnOrNull()
                val id = idInput?.toIntOrNull()

                if (id == null) {
                    println(" Invalid ID. Please enter a numeric value.")
                    continue
                }

                val employeeExists = employeeList.any { it.employeeId == id }
                if (!employeeExists) {
                    println(" No employee found with ID $id.")
                    continue
                }

                print("Enter check-in date & time (YYYY-MM-DD HH:MM) or press Enter for current time: ")
                val dateTimeInput = readlnOrNull()

                checkIn(id, dateTimeInput)
            }

            "2" -> {
                println("Exiting... Goodbye!")
                break
            }

            else -> {
                println(" Invalid choice. Please select 1 or 2.")
            }
        }
    }
}
