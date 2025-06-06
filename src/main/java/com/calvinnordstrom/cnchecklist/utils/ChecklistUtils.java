package com.calvinnordstrom.cnchecklist.utils;

import com.calvinnordstrom.cnchecklist.model.ChecklistItem;

public class ChecklistUtils {
    public static void printChecklist(ChecklistItem item) {
        System.out.println(item.getText());
        for (ChecklistItem subItem : item.getItems()) {
            printChecklistItem(subItem, 1);
        }
        System.out.println();
    }

    private static void printChecklistItem(ChecklistItem item, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "- " + item.getText());
        for (ChecklistItem subItem : item.getItems()) {
            printChecklistItem(subItem, depth + 1);
        }
    }
}
