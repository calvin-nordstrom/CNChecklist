module com.calvinnordstrom.cnchecklist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;


    opens com.calvinnordstrom.cnchecklist to javafx.fxml;
    exports com.calvinnordstrom.cnchecklist;
}