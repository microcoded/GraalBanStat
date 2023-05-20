package ge.goodrid.graalbanstat

import java.time.YearMonth

class MessageGenerator(
    private val previousMonthWarns: MutableMap<String, Int>,
    private val previousMonthBans: MutableMap<String, Int>,
    private val calcMonthWarns: MutableMap<String, Int>,
    private val calcMonthBans: MutableMap<String, Int>,
    private val prevMonth: String,
    private val calcMonth: String) {

    private var prevBanTotal = 0
    private var prevWarnTotal = 0
    private var calcBanTotal = 0
    private var calcWarnTotal = 0

    private lateinit var mY: String
    private lateinit var mY2: String
    private lateinit var previousMonth: String
    private lateinit var currentMonth: String

    fun generateMessage(): String {
        calculateTotals()
        dates()

        var msg = ""

        msg += "Hello everyone, the $mY ban statistics have been calculated! <@&185535444262322176> <@&605934575163670539>\n\n"
        msg += "In $currentMonth, we had $calcBanTotal bans and $calcWarnTotal warnings issued.\n"
        msg += "The lists below compare $mY2 to $mY. An up arrow (:uparrow:) indicates an increase, and a down arrow (:downarrow:) indicates a decrease.\n\n"
        msg += "**Bans**"

        return msg

    }

    private fun calculateTotals() {
        for ((_, value) in previousMonthBans.entries) {
            prevBanTotal += value
        }
        for ((_, value) in previousMonthWarns.entries) {
            prevWarnTotal += value
        }
        for ((_, value) in calcMonthBans.entries) {
            calcBanTotal += value
        }
        for ((_, value) in calcMonthWarns.entries) {
            calcWarnTotal += value
        }
    }

    private fun dates() {
        previousMonth = getFullMonthName(prevMonth)
        currentMonth = getFullMonthName(calcMonth)

        val ym = YearMonth.now().minusMonths(1)
        val year = ym.year
        mY = "$currentMonth $year"

        val ym2 = YearMonth.now().minusMonths(2)
        val year2 = ym2.year
        mY2 = "$previousMonth $year2"
    }

    private fun getFullMonthName(abbreviatedMonth: String): String {
        return when (abbreviatedMonth) {
            "Jan" -> "January"
            "Feb" -> "February"
            "Mar" -> "March"
            "Apr" -> "April"
            "May" -> "May"
            "Jun" -> "June"
            "Jul" -> "July"
            "Aug" -> "August"
            "Sep" -> "September"
            "Oct" -> "October"
            "Nov" -> "November"
            "Dec" -> "December"
            else -> throw IllegalArgumentException("Invalid month abbreviation: $abbreviatedMonth")
        }
    }

}
