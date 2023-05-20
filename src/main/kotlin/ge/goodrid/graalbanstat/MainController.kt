package ge.goodrid.graalbanstat

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage


class MainController {
    var model: MainModel = MainModel()

    @FXML
    fun aboutPressed() {
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("about.fxml"))
            val root1 = fxmlLoader.load<Any>() as Parent
            val stage = Stage()
            stage.scene = Scene(root1)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun closePressed() {
        model.exit()
    }

}