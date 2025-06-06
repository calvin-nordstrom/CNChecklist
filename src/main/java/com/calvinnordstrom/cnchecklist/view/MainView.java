package com.calvinnordstrom.cnchecklist.view;

import com.calvinnordstrom.cnchecklist.controller.ChecklistController;
import com.calvinnordstrom.cnchecklist.controller.MainController;
import com.calvinnordstrom.cnchecklist.model.ChecklistItem;
import com.calvinnordstrom.cnchecklist.model.MainModel;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    private final MainModel model;
    private final MainController controller;
    private final BorderPane view = new BorderPane();
    private final TabPane checklistTabPane = new TabPane();
    private final Map<Tab, ChecklistItem> tabChecklistMap = new HashMap<>();

    public MainView(MainModel model, MainController controller) {
        this.model = model;
        this.controller = controller;

        init();
        refresh();
    }

    private void init() {
        view.setTop(initMenuBar());
    }

    public void refresh() {
        checklistTabPane.getTabs().clear();

        for (ChecklistItem checklist : model.getChecklists()) {
            ChecklistController checklistController = new ChecklistController(checklist);
            ChecklistView checklistView = new ChecklistView(checklist, checklistController);
            Tab tab = new Tab(checklist.getText(), checklistView.asNode());
            tab.setClosable(false);
            checklist.textProperty().addListener((_, _, newValue) -> {
                tab.setText(newValue);
            });
            checklistTabPane.getTabs().add(tab);
            tabChecklistMap.put(tab, checklist);
        }

        view.setCenter(checklistTabPane);
    }

    public Node asNode() {
        return view;
    }

    private MenuBar initMenuBar() {
        MenuItem fileNew = new MenuItem("New");
        fileNew.setOnAction(_ -> {
            controller.createChecklist();
            refresh();
            checklistTabPane.getSelectionModel().selectLast();
        });

        MenuItem fileDelete = new MenuItem("Delete...");
        fileDelete.setOnAction(_ -> {
            ChecklistItem checklist = tabChecklistMap.get(
                    checklistTabPane.getSelectionModel().getSelectedItem());
            if (checklist != null) {
                String alertMessage = "Delete \"" + checklist.getText() + "\"?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(_ -> {
                            controller.deleteChecklist(checklist);
                            refresh();
                        }
                );
            }
        });

        MenuItem fileExit = new MenuItem("Exit");
        fileExit.setOnAction(_ -> controller.exit());

        Menu fileMenu = new Menu("File", null, fileNew, fileDelete, new SeparatorMenuItem(), fileExit);

        return new MenuBar(fileMenu);
    }
}
