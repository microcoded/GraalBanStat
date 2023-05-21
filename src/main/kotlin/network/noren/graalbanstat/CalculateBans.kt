package network.noren.graalbanstat

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class CalculateBans(val bansFile: File, val calcMonth: String) {

    private val compareMonth: String = previousMonth(calcMonth)

    private var previousMonthWarns: MutableMap<String, Int> = TreeMap()
    private var previousMonthBans: MutableMap<String, Int> = TreeMap()
    private var calcMonthWarns: MutableMap<String, Int> = TreeMap()
    private var calcMonthBans: MutableMap<String, Int> = TreeMap()

    lateinit var mg: MessageGenerator

    fun main(): String {
        setBansWarns()
        mg = MessageGenerator(previousMonthWarns, previousMonthBans, calcMonthWarns, calcMonthBans, compareMonth, calcMonth)
        return mg.generateMessage()
    }

    private fun setBansWarns() {
        val prevMth: Pair<MutableMap<String, Int>, MutableMap<String, Int>> = readFile(filterFile(bansFile, compareMonth))
        previousMonthBans = prevMth.first
        previousMonthWarns = prevMth.second

        val currMth: Pair<MutableMap<String, Int>, MutableMap<String, Int>> = readFile(filterFile(bansFile, calcMonth))
        calcMonthBans = currMth.first
        calcMonthWarns = currMth.second
    }

    private fun previousMonth(mth: String): String {
        val monthAbbreviations = listOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val currentMonthIndex = monthAbbreviations.indexOf(mth)
        val previousMonthIndex = (currentMonthIndex - 1 + 12) % 12
        return monthAbbreviations[previousMonthIndex]
    }


    private fun readFile(lines: List<String>): Pair<MutableMap<String, Int>, MutableMap<String, Int>> {
        val banCategories: MutableMap<String, Int> = TreeMap()
        val warnCategories: MutableMap<String, Int> = TreeMap()

        for (line in lines) {
            if (line.contains("comm banned")
                || line.contains("(npcserver) has")
                || line.contains("uploads")
                || line.contains("system has")
                || line.contains("GST")
            ) {
                // Ignore comm bans and system bans
                continue
            }
            val words = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (word in words) {
                try {
                    // Attempt to parse number
                    word.toDouble()
                } catch (e: NumberFormatException) {
                    // Not a number
                    if (word == "banned") {
                        // Banned with reason
                        var startIndex = line.indexOf("with reason: ")
                        if (startIndex != -1) {
                            startIndex += 13
                            val endIndex = line.indexOf(" (", startIndex)
                            val banCategory =
                                if (endIndex == -1) line.substring(startIndex) else line.substring(startIndex, endIndex)
                            banCategories[banCategory] = (banCategories[banCategory] ?: 0) + 1
                        }
                    } else if (word == "warning") {
                        // Warning with reason
                        var startIndex = line.indexOf("with reason: ")
                        if (startIndex != -1) {
                            startIndex += 13
                            val endIndex = line.indexOf(" (", startIndex)
                            val warningCategory =
                                if (endIndex == -1) line.substring(startIndex) else line.substring(startIndex, endIndex)
                            warnCategories[warningCategory] = (warnCategories[warningCategory] ?: 0) + 1
                        }
                    }
                }
            }
        }

        return Pair(banCategories, warnCategories)
    }


    fun filterFile(fileName: File, month: String): List<String> {
        val lines = mutableListOf<String>()
        val reader = BufferedReader(FileReader(fileName))
        var previousLine: String? = null

        var line: String? = reader.readLine()
        while (line != null) {
            if (previousLine != null && previousLine.contains(month)) {
                lines.add(previousLine)
                lines.add(line)
            }
            previousLine = line
            line = reader.readLine()
        }

        reader.close()

        return lines
    }

    fun getReversedWarnMap(): MutableMap<String, Int> {
        return calcMonthWarns.toSortedMap(Comparator.reverseOrder())
    }

    fun getReversedBanMap(): MutableMap<String, Int> {
        return calcMonthBans.toSortedMap(Comparator.reverseOrder())
    }
}