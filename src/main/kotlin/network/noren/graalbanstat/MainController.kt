package network.noren.graalbanstat

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.Region
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.File


class MainController {
    lateinit var outputTxtArea: TextArea
    lateinit var monthBox: ComboBox<String>
    private var stage: Stage? = null
    private lateinit var selectedFile: File

    fun setStage(stg: Stage) {
        this.stage = stg
    }

    @FXML
    lateinit var selectButton: Button


    private val model: MainModel = MainModel()

    @FXML
    fun initialize() {
        monthBox.items.addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        try {
            monthBox.selectionModel.select(model.getPreviousMonthAbbr())
        } catch (e: Exception) {
            // Date could not be automatically set from system for some reason.
            // Default text is shown instead.
        }
    }

    @FXML
    fun aboutPressed() {
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("about.fxml"))
            val root1 = fxmlLoader.load<Any>() as Parent
            val stage = Stage()
            stage.scene = Scene(root1)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.isResizable = false
            stage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @FXML
    fun closePressed() {
        model.exit()
    }

    @FXML
    fun select() {
        try {
            val fileChooser = FileChooser()
            fileChooser.title = "Select Bans File"
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Text Files", "*.txt"))

            selectedFile = fileChooser.showOpenDialog(stage)
            selectedFile.let {
                selectButton.text = selectedFile.name
            }
        } catch (e: Exception) {
            // Catch when box is exited but file isn't selected
        }
    }

    @FXML
    fun run() {
        if (this::selectedFile.isInitialized) {
            val cb = CalculateBans(selectedFile, monthBox.selectionModel.selectedItem)
            outputTxtArea.text = cb.main()
        } else {
            errorWindow("Please select bans file before continuing.")
        }
    }

    @FXML
    fun copyText() {
        val text = outputTxtArea.text
        val cb: Clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text)
        cb.setContent(content)
    }

    private fun errorWindow(err: String) {
        val alert = Alert(AlertType.NONE, err, ButtonType.OK)
        alert.dialogPane.minHeight = Region.USE_PREF_SIZE
        alert.show()
    }

}