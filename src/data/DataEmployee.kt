package data

data class DataEmployee(
    val id: String="",
    val firstName: String,
    val lastName: String,
    val role: Role,
    val reportingTo: Manager?
)