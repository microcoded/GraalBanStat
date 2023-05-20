package ge.goodrid.graalbanstat

import java.time.YearMonth
import kotlin.math.abs

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
        msg += "The lists below compare $mY2 to $mY. An up arrow (:uparrow:) indicates an increase, and a down arrow (:downarrow:) indicates a decrease. Any category with a total of 0 this month is excluded.\n\n"
        msg += "**Bans**\n"

        for ((category, banCount) in calcMonthBans.entries) {
            var increase: Double
            val oldBanCount = previousMonthBans.getOrDefault(category, 0)
            var trend: String
            var trendValue: String
            if (oldBanCount != 0) {
                increase = 100.0 * (banCount - oldBanCount) / oldBanCount
                trend = if (increase > 0) ":uparrow:" else if (increase < 0) ":downarrow:" else ":heavy_minus_sign:"
                trendValue = " (" + trend + String.format("%.1f", abs(increase)) + "%)"
            } else {
                trendValue = " (:uparrow: ∞%)"
            }
            if (calcMonthBans.containsKey(category)) {
                msg += "> *$category:*    $banCount $trendValue\n"
            }
        }

        msg += "\n**Warnings**\n"
        for ((category, warnCount) in calcMonthWarns.entries) {
            var increase: Double
            var trend: String
            var trendValue: String
            val oldWarnCount = previousMonthWarns.getOrDefault(category, 0)
            if (oldWarnCount != 0) {
                increase = 100.0 * (warnCount - oldWarnCount) / oldWarnCount
                trend = if (increase > 0) ":uparrow:" else if (increase < 0) ":downarrow:" else ":heavy_minus_sign:"
                trendValue = " (" + trend + String.format("%.1f", abs(increase)) + "%)"
            } else {
                trendValue = " (:uparrow: ∞%)"
            }
            if (calcMonthWarns.containsKey(category)) {
                msg += "> *$category:*    $warnCount $trendValue\n"
            }
        }


        return msg.replace("\n", System.getProperty("line.separator"))

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
