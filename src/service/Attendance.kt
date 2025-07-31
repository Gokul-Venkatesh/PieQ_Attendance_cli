package service

import data.DataAttendance
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Duration

class Attendance {

    private val checkInList = mutableListOf<DataAttendance>()

    fun checkIn(employeeId: String, dateTime: LocalDateTime): Boolean {
        val checkInDate = dateTime.toLocalDate()

        if (hasAlreadyCheckedIn(employeeId, checkInDate)) {
            println("Employee $employeeId has already checked in on $checkInDate.")
            return false
        }

        val attendance = DataAttendance(employeeId, dateTime)
        checkInList.add(attendance)
        println("added")
        return true
    }



    fun checkOut(employeeId: String, dateTime: LocalDateTime): Boolean {
        val record = checkInList.find {
            it.employeeId == employeeId &&
                    it.dateTimeOfCheckIn.toLocalDate() == dateTime.toLocalDate() &&
                    it.dateTimeOfCheckOut == null
        }

        return if (record != null) {
            // ✅ REMOVE the record after successful check-out
            checkInList.remove(record)
            true
        } else {
            println("Check-out failed: No check-in found for Employee $employeeId on ${dateTime.toLocalDate()}")
            false
        }
    }


    fun hasAlreadyCheckedIn(employeeId: String, date: LocalDate): Boolean {
        return checkInList.any {
            it.employeeId == employeeId && it.dateTimeOfCheckIn.toLocalDate() == date
        }
    }

    fun hasAlreadyCheckedOut(employeeId: String, date: LocalDate): Boolean {
        return checkInList.any {
            it.employeeId == employeeId &&
                    it.dateTimeOfCheckIn.toLocalDate() == date &&
                    it.dateTimeOfCheckOut != null
        }
    }

    fun getAttendanceRecords(): List<DataAttendance> = checkInList

    fun getWorkDuration(employeeId: String, date: LocalDate): String {
        val record = checkInList.find {
            it.employeeId == employeeId &&
                    it.dateTimeOfCheckIn.toLocalDate() == date &&
                    it.dateTimeOfCheckOut != null
        }

        return if (record != null) {
            val duration = Duration.between(record.dateTimeOfCheckIn, record.dateTimeOfCheckOut)
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            "Employee $employeeId worked for ${"%02d".format(hours)}h ${"%02d".format(minutes)}m on $date"
        } else {
            "No complete check-in/check-out found for Employee $employeeId on $date"
        }
    }
}
