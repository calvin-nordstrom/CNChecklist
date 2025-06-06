package com.calvinnordstrom.cnchecklist.view;

import javafx.css.Styleable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

public class ViewUtils {
    public static Button createIconButton(FontIcon icon) {
        Button button = new Button();
        button.setGraphic(icon);
        button.setBackground(null);
        button.setPadding(new Insets(0));
        return button;
    }

    public static void addStyles(Styleable component, String... styles) {
        component.getStyleClass().addAll(styles);
    }
}
