package com.calvinnordstrom.cnchecklist.controller;

import com.calvinnordstrom.cnchecklist.model.ChecklistItem;

public class ChecklistController {
    private final ChecklistItem model;

    public ChecklistController(ChecklistItem model) {
        this.model = model;
    }

    public void setText(ChecklistItem item, String text) {
        item.setText(text);
    }

    public void createItem(ChecklistItem parent, ChecklistItem child) {
        model.createItem(parent, child);
    }

    public void check(ChecklistItem parent, boolean checked) {
        parent.check(checked);
    }

    public void deleteItem(ChecklistItem item) {
        model.deleteItem(item);
    }
}
