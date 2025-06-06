package com.calvinnordstrom.cnchecklist.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChecklistItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient StringProperty text;
    private transient BooleanProperty checked = new SimpleBooleanProperty();
    private final List<ChecklistItem> items = new ArrayList<>();

    public ChecklistItem() {
        this("");
    }

    public ChecklistItem(String text) {
        this.text = new SimpleStringProperty(text);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

    public boolean createItem(ChecklistItem parent, ChecklistItem child) {
        if (parent == this) {
            items.add(child);
            return true;
        }

        for (ChecklistItem item : items) {
            if (item.createItem(parent, child)) {
                return true;
            }
        }

        return false;
    }

    public void check(boolean checked) {
        setChecked(checked);

        for (ChecklistItem item : items) {
            item.check(checked);
        }
    }

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
        out.writeObject(text.get());
        out.writeBoolean(checked.get());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        text = new SimpleStringProperty((String) in.readObject());
        checked = new SimpleBooleanProperty(in.readBoolean());
    }
}
