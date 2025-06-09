package com.calvinnordstrom.cnchecklist.controller;

import com.calvinnordstrom.cnchecklist.model.ChecklistItem;
import com.calvinnordstrom.cnchecklist.model.MainModel;

/**
 * A class that provides methods to manipulate a {@link MainModel} object,
 * including creating a new checklist, deleting a checklist, and exiting the
 * main program. It serves as a controller in an MVC-style patterns by
 *  * delegating operations from the view to the model.
 */
public class MainController {
    private final MainModel model;

    /**
     * Constructs a new {@code MainController} with the given model.
     *
     * @param model the {@link MainModel} model
     */
    public MainController(MainModel model) {
        this.model = model;
    }

    /**
     * Creates a new checklist and adds it to the model.
     */
    public void createChecklist() {
        model.addChecklist(new ChecklistItem("New Checklist"));
    }

    /**
     * Deletes the specified {@link ChecklistItem}.
     *
     * @param checklist the {@link ChecklistItem} to delete
     */
    public void deleteChecklist(ChecklistItem checklist) {
        model.deleteChecklist(checklist);
    }

    /**
     * Shuts down the main program. This method delegates the shutdown call to
     * the model, so it may properly terminate.
     */
    public void exit() {
        model.shutdown();
    }
}
