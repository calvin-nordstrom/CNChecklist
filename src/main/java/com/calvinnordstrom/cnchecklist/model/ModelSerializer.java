package com.calvinnordstrom.cnchecklist.model;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ModelSerializer {
    private static final String FILE_PATH = "checklists.ser";
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void start(List<ChecklistItem> checklists, int ms) {
        scheduler.scheduleAtFixedRate(() -> {
            save(checklists);
        }, ms, ms, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }

    public static void save(List<ChecklistItem> checklists) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(checklists);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
