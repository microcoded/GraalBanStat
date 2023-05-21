package network.noren.graalbanstat

import java.awt.Desktop
import java.io.File


class CSVGenerator(
    private val bansMap: MutableMap<String, Int>,
    private val warnsMap: MutableMap<String, Int>,
    private val currentMonthYear: String) {
    fun main() {
        val reversedBansMap = bansMap.toList()
            .sortedByDescending { it.first }
            .toMap()
            .toList()

        val reversedWarnsMap = warnsMap.toList()
            .sortedByDescending { it.first }
            .toMap()
            .toList()

        try {
            val home = System.getProperty("user.home")
            val csvFile = File("$home/Downloads/output.csv")

            csvFile.bufferedWriter().use { writer ->
                writer.write("Classic iPhone Ban Statistics - $currentMonthYear,\n\n")
                writer.write("Category,Bans,Warns\n")
                for ((category, bans) in reversedBansMap) {
                    val warns = warnsMap[category]
                    if (warns == null) {
                        writer.write("$category,$bans,\n")
                    } else {
                        writer.write("$category,$bans,$warns\n")
                    }
                }
                // Open the CSV file with Microsoft Excel
                Desktop.getDesktop().open(csvFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
