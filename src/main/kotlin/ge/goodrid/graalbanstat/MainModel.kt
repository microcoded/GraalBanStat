package ge.goodrid.graalbanstat

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainModel : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainModel::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "GraalBanStat"
        stage.scene = scene
        stage.show()
    }

    fun exit() {
        Platform.exit()
    }
}

fun main() {
    Application.launch(MainModel::class.java)
}