package network.noren.graalbanstat

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.StackedBarChart
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.stage.Stage


class GraphGenerator (mg: MessageGenerator) {
    val previousMonthWarns: MutableMap<String, Int> = mg.previousMonthWarns
    val previousMonthBans: MutableMap<String, Int> = mg.previousMonthBans
    val calcMonthWarns: MutableMap<String, Int> = mg.calcMonthWarns
    val calcMonthBans: MutableMap<String, Int> = mg.calcMonthBans
    val prevMonth: String = mg.prevMonth
    val calcMonth: String = mg.calcMonth
    val prevBanTotal = mg.prevBanTotal
    val prevWarnTotal = mg.prevWarnTotal
    val calcBanTotal = mg.calcBanTotal
    val calcWarnTotal = mg.calcWarnTotal
    val mY: String = mg.mY
    val mY2: String = mg.mY2
    val previousMonth: String = mg.previousMonth
    val currentMonth: String = mg.currentMonth

    fun start() {
        print(calcMonth)
    }
}