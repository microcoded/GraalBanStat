module ge.goodrid.graalbanstat {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;

    opens ge.goodrid.graalbanstat to javafx.fxml;
    exports ge.goodrid.graalbanstat;
}