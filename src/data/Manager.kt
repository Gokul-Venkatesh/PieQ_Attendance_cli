package data

enum class Manager(val id: String, val fullName: String) {
    AH101("ah101", "Ali Hassan"),
    GV211("gv211", "Guru Varadhan"),
    SA112("sa112", "Sam Alex"),
    RK312("rk312", "Ravi Kumar"),
    HB901("hb901", "Hassan Babu");

    override fun toString(): String = "$fullName ($id)"

    companion object {
        fun fromId(id: String): Manager? {
            return values().find { it.id.equals(id.trim(), ignoreCase = true) }
        }

        fun printAll() {
            println("📋 Available Managers:")
            values().forEach { println("- $it") }
        }
    }
}
