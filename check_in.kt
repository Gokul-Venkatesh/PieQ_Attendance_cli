import java.time.LocalDate
import java.time.LocalTime

data class Employee(
    val employeeId: Int,
    val firstName: String,
    val lastName: String,
    val jobRole: String,
    val managerId: Int
)

data class Attendance(
    val employeeId: Int,
    val dateOfCheckIn: String,
    val timeOfCheckIn: String
)

val employeeList = mutableListOf<Employee>()
val checkInList = mutableListOf<Attendance>()

fun getCurrentDate(): String = LocalDate.now().toString()
fun getCurrentTime(): String = LocalTime.now().toString()

fun checkIn(id: Int) {
    // Check if employeeId is valid and add it to checkinlist
    if (validateCheckIn(id)) {
        val employee = employeeList.find { it.employeeId == id }

        if (employee != null) {
            val date = getCurrentDate()
            val time = getCurrentTime()
            checkInList.add(Attendance(id, date, time))
            println("Checked in: ${employee.firstName} ${employee.lastName}, Role: ${employee.jobRole} , ManagerID: ${employee.managerId}")
        }
    }
}
//valid employee ckecks valid check in or already checked in or invalid check in
fun validateCheckIn(id: Int): Boolean {
    val employee = employeeList.find { it.employeeId == id }

    return if (employee != null) {
        val alreadyCheckedIn = checkInList.any {
            it.employeeId == id && it.dateOfCheckIn == getCurrentDate()
        }
        if (alreadyCheckedIn) {
            println("Employee already checked in today.")
            false
        } else {
            true
        }
    } else {
        println("Invalid check-in: ID not found.")
        false
    }
}
fun main() {
    // Sample data
    employeeList.add(Employee(1, "Ajith", "Kumar", "Developer", 121))
    employeeList.add(Employee(2, "Tom", "Cruise", "Tester", 121))
    employeeList.add(Employee(3, "Tony", "Stark", "Project Manager", 100))
    employeeList.add(Employee(4, "Dwayne", "Johnson", "Designer", 403))
    employeeList.add(Employee(5, "Emma", "Watson", "Developer", 121))

    while (true) {
        println("Enter employee ID to check in:")
        val input = readln()
        val id = input.toIntOrNull()

        if (id != null) {
            checkIn(id)
        } else {
            println("Invalid input. Please enter a valid number.")
        }
    }
}
