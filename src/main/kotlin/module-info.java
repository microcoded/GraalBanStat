module ge.goodrid.graalbanstat {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires javafx.base;
    requires javafx.graphics;

    requires java.desktop;

    opens network.noren.graalbanstat to javafx.fxml;
    exports network.noren.graalbanstat;
}