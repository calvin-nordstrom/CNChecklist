package com.calvinnordstrom.cnchecklist.view;

import com.calvinnordstrom.cnchecklist.controller.ChecklistController;
import com.calvinnordstrom.cnchecklist.model.ChecklistItem;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.calvinnordstrom.cnchecklist.view.ViewUtils.addStyles;

public class ChecklistView {
    private final ChecklistItem model;
    private final ChecklistController controller;
    private final BorderPane view = new BorderPane();

    public ChecklistView(ChecklistItem model, ChecklistController controller) {
        this.model = model;
        this.controller = controller;

        init();
    }

    private void init() {
        ChecklistTreeView itemView = new ChecklistTreeView(model);
        ScrollPane scrollPane = new ScrollPane(itemView.asNode());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        view.setCenter(scrollPane);

        addStyles(view, "checklist-view");
    }

    public Node asNode() {
        return view;
    }

    private class ChecklistTreeView {
        private final ChecklistItem item;
        private final BorderPane view = new BorderPane();
        private final VBox center = new VBox();

        private ChecklistTreeView(ChecklistItem item) {
            this.item = item;

            refresh();
        }

        public void refresh() {
            center.getChildren().clear();

            ChecklistTreeItem rootItem = new ChecklistTreeItem(item);
            center.getChildren().add(rootItem.asNode());

            for (ChecklistItem item : item.getItems()) {
                populateChildren(item, 1);
            }

            view.setCenter(center);

            addStyles(center, "checklist-tree-view");
            addStyles(rootItem.asNode(), "checklist-tree-item-full", item.isChecked() ? "checked" : "unchecked");
        }

        private void populateChildren(ChecklistItem item, int depth) {
            ChecklistTreeItem treeItem = new ChecklistTreeItem(item);

            HBox spacer = new HBox();
            spacer.setMinWidth(24 * depth);

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(treeItem.asNode());
            borderPane.setLeft(spacer);

            center.getChildren().add(borderPane);

            addStyles(borderPane, "checklist-tree-item-full", item.isChecked() ? "checked" : "unchecked");

            for (ChecklistItem subItem : item.getItems()) {
                populateChildren(subItem, depth + 1);
            }
        }

        public Node asNode() {
            return view;
        }

        private class ChecklistTreeItem {
            private final ChecklistItem item;
            private final BorderPane view = new BorderPane();

            private ChecklistTreeItem(ChecklistItem item) {
                this.item = item;

                init();
            }

            private void init() {
                Button createButton = ViewUtils.createIconButton(FontIcon.of(BootstrapIcons.PLUS));
                createButton.setTooltip(new Tooltip("New sub-item"));
                createButton.setFocusTraversable(false);
                createButton.setOnMouseClicked(_ -> {
                    controller.createItem(item, new ChecklistItem());
                    refresh();
                });

                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(item.isChecked());
                checkBox.setFocusTraversable(false);
                checkBox.selectedProperty().addListener((_, _, newValue) -> {
                    controller.check(item, newValue);
                    refresh();
                });

                TextField textField = new TextField(item.getText());
                textField.textProperty().addListener((_, _, newValue) -> {
                    controller.setText(item, newValue);
                });
                textField.setPrefWidth(10000);

                HBox center = new HBox(createButton, checkBox, textField);
                view.setCenter(center);

                Button deleteButton = ViewUtils.createIconButton(FontIcon.of(BootstrapIcons.TRASH));
                deleteButton.setTooltip(new Tooltip("Delete item"));
                deleteButton.setFocusTraversable(false);
                deleteButton.setOnMouseClicked(_ -> {
                    controller.deleteItem(item);
                    refresh();
                });

                HBox right = new HBox(deleteButton);
                if (item != model) {
                    view.setRight(right);
                }

                addStyles(view, "checklist-tree-item");
                addStyles(center, "checklist-tree-item-center", "center-left");
                addStyles(createButton, "checklist-tree-item-button");
                addStyles(checkBox, "checklist-tree-item-checkbox");
                addStyles(textField, "checklist-tree-item-textfield");
                addStyles(deleteButton, "checklist-tree-item-button");
                addStyles(right, "checklist-tree-item-right", "center-right");
            }

            public Node asNode() {
                return view;
            }
        }
    }
}
