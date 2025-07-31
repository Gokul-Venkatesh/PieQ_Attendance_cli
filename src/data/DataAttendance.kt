package data

import java.time.LocalDateTime

data class DataAttendance(
    val employeeId: String,
    val dateTimeOfCheckIn: LocalDateTime,
    var dateTimeOfCheckOut: LocalDateTime? = null
    //working hours
)