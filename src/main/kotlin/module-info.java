module ge.goodrid.graalbanstat {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;

    opens network.noren.graalbanstat to javafx.fxml;
    exports network.noren.graalbanstat;
}