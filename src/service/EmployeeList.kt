package service

import data.DataEmployee
import data.Manager
import data.Role

class EmployeeList : ArrayList<DataEmployee>() {

    override fun add(employee: DataEmployee): Boolean {
        // --- Validation ---
        if (employee.firstName.isBlank() || employee.lastName.isBlank()) {
            println("First name and last name cannot be blank.")
            return false
        }

        if (employee.firstName.all { it.isDigit() } || employee.lastName.all { it.isDigit() }) {
            println("First name and last name cannot be only numbers.")
            return false
        }

        if (employee.role !in Role.entries) {
            println("Invalid role: ${employee.role}")
            return false
        }

        if (employee.role != Role.MANAGER && (employee.reportingTo !in Manager.entries)) {
            println("Invalid manager: ${employee.reportingTo}")
            return false
        }


        return super.add(employee)
    }
}