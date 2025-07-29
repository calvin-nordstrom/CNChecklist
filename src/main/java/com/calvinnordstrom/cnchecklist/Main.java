package com.calvinnordstrom.cnchecklist;

import com.calvinnordstrom.cnchecklist.controller.MainController;
import com.calvinnordstrom.cnchecklist.model.MainModel;
import com.calvinnordstrom.cnchecklist.model.ModelSerializer;
import com.calvinnordstrom.cnchecklist.view.MainView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());
    private static final String TITLE = "CNChecklist";
    private static final String VERSION = "1.0.4";
    private static final double WIDTH = 540;
    private static final double HEIGHT = 480;
    private static final double MIN_WIDTH = 270;
    private static final double MIN_HEIGHT = 240;
    private final MainModel model = new MainModel();
    private final MainController controller = new MainController(model);
    private final MainView view = new MainView(model, controller);
    private final ModelSerializer modelSerializer = new ModelSerializer();

    @Override
    public void init() {
        modelSerializer.start(model.getChecklists(), 1000);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene((Parent) view.asNode());
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle(TITLE + " " + VERSION);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setOnHidden(_ -> Platform.exit());
        stage.show();
    }

    @Override
    public void stop() {
        modelSerializer.stop();
        ModelSerializer.save(model.getChecklists());

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}