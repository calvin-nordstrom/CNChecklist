package com.calvinnordstrom.cnchecklist.model;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * The main model class containing all {@link ChecklistItem} checklists. This
 * class serves as the main model in an MVC-style pattern, where it contains
 * all usable data structures for the checklists. It also manages the
 * serialization and deserialization of checklists.
 */
public class MainModel {
    private final List<ChecklistItem> checklists;

    /**
     * Constructs a new {@code MainModel} instance. The  list of checklists
     * is loaded from the database upon instantiation. If no checklists are
     * found or the database does not exist, a new checklist with no children
     * will be added.
     */
    public MainModel() {
        List<ChecklistItem> loaded = ModelSerializer.load();
        if (loaded != null) {
            this.checklists = loaded;
        } else {
            this.checklists = new ArrayList<>();
            checklists.add(new ChecklistItem("New Checklist"));
        }
    }

    /**
     * Appends the specified {@code ChecklistItem} to the end of the list.
     *
     * @param checklist the {@code ChecklistItem} to add
     */
    public void addChecklist(ChecklistItem checklist) {
        checklists.add(checklist);
    }

    /**
     * Deletes the specified {@code ChecklistItem} from the list. If the given
     * checklist does not exist in the list, no changes will be made.
     *
     * @param checklist the {@code ChecklistItem} to delete
     */
    public void deleteChecklist(ChecklistItem checklist) {
        checklists.remove(checklist);
    }

    /**
     * Returns the list of {@code ChecklistItem} objects.
     *
     * @return the list of {@code ChecklistItem} objects
     */
    public List<ChecklistItem> getChecklists() {
        return checklists;
    }

    /**
     * Shuts down the main program. This method is used to clean everything
     * before shutting down.
     */
    public void shutdown() {
        Platform.exit();
    }
}
