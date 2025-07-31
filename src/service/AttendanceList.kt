package service

import java.time.LocalDate
import java.time.LocalDateTime
import data.*
class AttendanceList : ArrayList<DataAttendance>() {

    override fun add(attendance: DataAttendance): Boolean {
        val checkInDate = attendance.dateTimeOfCheckIn.toLocalDate()

        if (hasAlreadyCheckedIn(attendance.employeeId, checkInDate)) {
            println("Employee ${attendance.employeeId} has already checked in on $checkInDate.")
            return false
        }

        return super.add(attendance)
    }

    fun hasAlreadyCheckedIn(employeeId: String, date: LocalDate): Boolean {
        return this.any {
            it.employeeId == employeeId &&
                    it.dateTimeOfCheckIn.toLocalDate() == date
        }
    }

    fun hasAlreadyCheckedOut(employeeId: String, date: LocalDate): Boolean {
        return this.any {
            it.employeeId == employeeId &&
                    it.dateTimeOfCheckIn.toLocalDate() == date &&
                    it.dateTimeOfCheckOut != null
        }
    }
}
