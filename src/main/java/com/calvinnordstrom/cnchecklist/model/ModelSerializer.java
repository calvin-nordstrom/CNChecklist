package com.calvinnordstrom.cnchecklist.model;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The {@code ModelSerializer} class handles periodic saving and loading of a
 * list of {@link ChecklistItem} objects to and from a file using Java
 * serialization. It supports automatic saving at a fixed interval and manual
 * load/save operations.
 */
public class ModelSerializer {
    private static final String FILE_PATH = "checklists.ser";
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * Starts the periodic saving of the provided checklist list to disk.
     *
     * @param checklists the list of checklist items to be saved
     * @param ms the interval in milliseconds between each save
     */
    public void start(List<ChecklistItem> checklists, int ms) {
        scheduler.scheduleAtFixedRate(() -> {
            save(checklists);
        }, ms, ms, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the periodic saving scheduler.
     */
    public void stop() {
        scheduler.shutdown();
    }

    /**
     * Serializes and saves the provided list of {@link ChecklistItem}s to
     * disk.
     *
     * @param checklists the list of checklist items to save
     */
    public static void save(List<ChecklistItem> checklists) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(checklists);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Loads the list of {@link ChecklistItem}s from disk, if available.
     *
     * @return the deserialized list of checklist items, or {@code null} if
     * loading fails
     */
    @SuppressWarnings("unchecked")
    public static List<ChecklistItem> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<ChecklistItem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
