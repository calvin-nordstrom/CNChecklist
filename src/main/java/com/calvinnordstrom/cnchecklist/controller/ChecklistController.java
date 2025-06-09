package com.calvinnordstrom.cnchecklist.controller;

import com.calvinnordstrom.cnchecklist.model.ChecklistItem;

/**
 * A class that provides methods to manipulate {@link ChecklistItem} objects,
 * including setting the text, creating items, checking/unchecking, and
 * deleting items. It serves as a controller in an MVC-style patterns by
 * delegating operations from the view to the model.
 */
public class ChecklistController {
    private final ChecklistItem model;

    /**
     * Constructs a new {@code ChecklistController} with the given model.
     *
     * @param model the root {@link ChecklistItem} to be the model
     */
    public ChecklistController(ChecklistItem model) {
        this.model = model;
    }

    /**
     * Sets the text of the specified {@link ChecklistItem}.
     *
     * @param item the checklist item whose text should be set
     * @param text the new text to set on the item
     */
    public void setText(ChecklistItem item, String text) {
        item.setText(text);
    }

    /**
     * Sets the checked state of the specified {@link ChecklistItem}.
     *
     * @param parent the item to be checked or unchecked
     * @param checked {@code true} to check the item, {@code false} to uncheck it
     */
    public void check(ChecklistItem parent, boolean checked) {
        parent.check(checked);
    }

    /**
     * Creates a new child {@link ChecklistItem} under a parent item.
     *
     * @param parent the parent item under which the child should be created
     * @param child the child item to be added
     */
    public void createItem(ChecklistItem parent, ChecklistItem child) {
        model.createItem(parent, child);
    }

    /**
     * Deletes the specified {@link ChecklistItem} from the model.
     *
     * @param item the item to be deleted
     */
    public void deleteItem(ChecklistItem item) {
        model.deleteItem(item);
    }
}
