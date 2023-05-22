package network.noren.graalbanstat

import javafx.fxml.FXML
import javafx.scene.chart.StackedBarChart
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series


class GraphController {


    @FXML
    lateinit var banWarnChart: StackedBarChart<Int, String>

    private lateinit var gg: GraphGenerator

    private var banSeries: Series<Int, String> = Series<Int, String>()
    private var warnSeries: Series<Int, String> = Series<Int, String>()

    @FXML
    fun initialize() {
    }

    fun initData(gg: GraphGenerator) {
        this.gg = gg
        banWarnChart.title = "Classic iPhone Ban Statistics — ${gg.mY}"

        banSeries.name = "Bans"
        warnSeries.name = "Warns"

        for ((s, i) in gg.calcMonthBans) {
            val d = XYChart.Data(i, s)
            banSeries.data.add(d)
        }

        for ((s, i) in gg.calcMonthWarns) {
            val d = XYChart.Data(i, s)
            warnSeries.data.add(d)
        }

        banWarnChart.data.addAll(banSeries, warnSeries)
    }

}