package com.calvinnordstrom.cnchecklist.controller;

import com.calvinnordstrom.cnchecklist.model.ChecklistItem;
import com.calvinnordstrom.cnchecklist.model.MainModel;

public class MainController {
    private final MainModel model;

    public MainController(MainModel model) {
        this.model = model;
    }

    public void createChecklist() {
        model.addChecklist(new ChecklistItem("New Checklist"));
    }

    public void deleteChecklist(ChecklistItem checklist) {
        model.deleteChecklist(checklist);
    }

    public void exit() {
        model.shutdown();
    }
}
