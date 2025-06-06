package com.calvinnordstrom.cnchecklist.model;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private final List<ChecklistItem> checklists;

    public MainModel() {
        List<ChecklistItem> loaded = ModelSerializer.load();
        if (loaded != null) {
            this.checklists = loaded;
        } else {
            this.checklists = new ArrayList<>();
            checklists.add(new ChecklistItem("New Checklist"));
        }
    }

    public void addChecklist(ChecklistItem checklist) {
        checklists.add(checklist);
    }

    public void deleteChecklist(ChecklistItem checklist) {
        checklists.remove(checklist);
    }

    public List<ChecklistItem> getChecklists() {
        return checklists;
    }

    public void shutdown() {
        Platform.exit();
    }

    private void populateTestData() {
        ChecklistItem checklist1 = new ChecklistItem("Testing Checklist");
        ChecklistItem groceries = new ChecklistItem("Groceries");
        ChecklistItem fruits = new ChecklistItem("Fruits");
        fruits.getItems().add(new ChecklistItem("Raspberries"));
        fruits.getItems().add(new ChecklistItem("Blueberries"));
        ChecklistItem vegetables = new ChecklistItem("Vegetables");
        vegetables.getItems().add(new ChecklistItem("Broccoli"));
        groceries.getItems().add(fruits);
        groceries.getItems().add(vegetables);
        checklist1.getItems().add(groceries);
//        ChecklistUtils.printChecklist(checklist1);
        checklists.add(checklist1);

        ChecklistItem checklist2 = new ChecklistItem("Weekly Assignments");
        ChecklistItem courses = new ChecklistItem("Assignments");
        ChecklistItem cmpen461 = new ChecklistItem("CMPEN 461");
        cmpen461.getItems().add(new ChecklistItem("Quiz"));
        ChecklistItem cmpsc465 = new ChecklistItem("CMPSC 465");
        cmpsc465.getItems().add(new ChecklistItem("Assignment"));
        cmpsc465.getItems().add(new ChecklistItem("Quiz"));
        courses.getItems().add(cmpen461);
        courses.getItems().add(cmpsc465);
        checklist2.getItems().add(courses);
        checklists.add(checklist2);
    }
}
