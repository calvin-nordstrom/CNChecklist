package com.calvinnordstrom.cnchecklist.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item in a tree graph checklist structure. Each
 * {@code ChecklistItem} supports an associated text label, checked state, and
 * child items of the same type. This class also supports serialization and
 * some JavaFX property bindings.
 */
public class ChecklistItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient StringProperty text;
    private transient BooleanProperty checked;
    private final List<ChecklistItem> items = new ArrayList<>();

    /**
     * Constructs an empty {@code ChecklistItem} with no text.
     */
    public ChecklistItem() {
        this("");
    }

    /**
     * Constructs a {@code ChecklistItem} with the specified text.
     *
     * @param text the text to display for this item
     */
    public ChecklistItem(String text) {
        this.text = new SimpleStringProperty(text);
    }

    /**
     * Returns the text of this checklist item.
     *
     * @return the item's text
     */
    public String getText() {
        return textProperty().get();
    }

    /**
     * Sets the text of this checklist item.
     *
     * @param text the new text to set
     */
    public void setText(String text) {
        textProperty().set(text);
    }

    /**
     * Returns the JavaFX {@code StringProperty} for binding the text.
     *
     * @return the text property
     */
    public StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty(this, "text", "");
        }
        return text;
    }

    /**
     * Returns whether this item is checked.
     *
     * @return {@code true} if checked, {@code false} otherwise
     */
    public boolean isChecked() {
        return checkedProperty().get();
    }

    /**
     * Sets the checked state of this item.
     *
     * @param checked {@code true} to mark as checked, {@code false} to uncheck
     */
    public void setChecked(boolean checked) {
        checkedProperty().set(checked);
    }

    /**
     * Returns the JavaFX {@code BooleanProperty} for binding the checked state.
     *
     * @return the checked property
     */
    public BooleanProperty checkedProperty() {
        if (checked == null) {
            checked = new SimpleBooleanProperty(this, "checked", false);
        }
        return checked;
    }

    /**
     * Returns the list of child items contained within this checklist item.
     *
     * @return the list of child {@code ChecklistItem}s
     */
    public List<ChecklistItem> getItems() {
        return items;
    }

    /**
     * Sets the checked state of this item and recursively applies
     * the same state to all of its child items.
     *
     * @param checked {@code true} to check all items, {@code false} to uncheck
     */
    public void check(boolean checked) {
        setChecked(checked);

        for (ChecklistItem item : items) {
            item.check(checked);
        }
    }

    /**
     * Attempts to add a child item under the given parent within this hierarchy.
     * If the parent is found (recursively), the child is added to it.
     *
     * @param parent the parent under which the child should be added
     * @param child the child item to add
     * @return {@code true} if the child was successfully added, {@code false} otherwise
     */
    public boolean createItem(ChecklistItem parent, ChecklistItem child) {
        if (this == parent) {
            items.add(child);
            return true;
        }

        for (ChecklistItem item : items) {
            if (item == parent) {
                item.items.add(child);
                return true;
            }

            if (item.createItem(parent, child)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Attempts to delete the specified target item from this hierarchy.
     * If the item is found (recursively), it is removed from its parent's list.
     *
     * @param target the item to remove
     * @return {@code true} if the item was found and removed, {@code false} otherwise
     */
    public boolean deleteItem(ChecklistItem target) {
        if (items.remove(target)) {
            return true;
        }

        for (ChecklistItem item : items) {
            if (item.deleteItem(target)) {
                return true;
            }
        }

        return false;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(getText());
        out.writeBoolean(isChecked());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setText((String) in.readObject());
        setChecked(in.readBoolean());
    }
}
