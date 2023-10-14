module com.github.ammardevz.rodus {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    exports com.github.ammardevz.rodus;
    opens com.github.ammardevz.rodus to javafx.fxml;

}