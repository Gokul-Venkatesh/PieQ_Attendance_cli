package service

import data.*

class Employee(private val employeeList: EmployeeList) {

    // Private counter (starting at 1)
    private var idCounter = 1

    fun addEmployee(
        firstName: String,
        lastName: String,
        role: Role,
        reportingTo: Manager?
    ): String? {
        val tempEmployee = DataEmployee(
            id = "", // temp
            firstName = firstName,
            lastName = lastName,
            role = role,
            reportingTo = reportingTo
        )

        val isValid = employeeList.add(tempEmployee)
        if (!isValid) {
            println("Employee not added. Validation failed.")
            return null
        }

        // Generate ID using private counter
        val generatedId = generateEmployeeId(firstName, lastName)

        val finalEmployee = tempEmployee.copy(id = generatedId)
        employeeList.add(finalEmployee)

        println("Employee $firstName $lastName added with $generatedId")
        idCounter++  // Increment only after adding
        return generatedId
    }

    private fun generateEmployeeId(first: String, last: String): String {
        val initials = first.trim().first().uppercaseChar().toString() +
                last.trim().first().uppercaseChar()
        return initials + idCounter.toString().padStart(3, '0')
    }

    fun getAllEmployees(): List<DataEmployee> {
        return employeeList
    }

    fun employeeExists(id: String): Boolean {
        return employeeList.any { it.id == id }
    }
}

