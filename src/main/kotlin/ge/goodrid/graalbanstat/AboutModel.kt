package ge.goodrid.graalbanstat

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class AboutModel : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(AboutModel::class.java.getResource("about.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "About"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(AboutModel::class.java)
}