package network.noren.graalbanstat

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class MainModel : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainModel::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load())
        val controller: MainController = fxmlLoader.getController()

        stage.title = "GraalBanStat"
        stage.scene = scene

        controller.setStage(stage)
        stage.show()
    }

    fun exit() {
        Platform.exit()
    }

    fun getPreviousMonthAbbr(): String {
        val previousMonth = LocalDate.now().minusMonths(1).month
        return previousMonth.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    }

}

fun main() {
    Application.launch(MainModel::class.java)
}