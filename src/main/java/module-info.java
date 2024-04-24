module IMS.inventorymgmtsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires fontawesomefx;
    requires java.sql;
    requires java.desktop;
    requires aspose.cells;
    opens IMS.GUI to javafx.fxml;
    opens IMS.data to javafx.base;
    exports IMS.GUI;
}