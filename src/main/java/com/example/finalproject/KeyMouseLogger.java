package com.example.finalproject;

import javafx.scene.Scene;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class KeyMouseLogger {
    private boolean onlyMouse = true;
    private boolean onlyKeyboard = true;

    private long maxFileSize = 50 * 1024 * 1024;
    private final File logFile;
    private FileWriter fileWriter;
    private BufferedWriter writer;

    private Scene scene;

    public KeyMouseLogger() {

        logFile = new File("log.txt");
        try {
            fileWriter = new FileWriter(logFile, true);
            writer = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        scene.setOnKeyPressed(null);
        scene.setOnMouseClicked(null);
    }

    public void start() {
        scene.setOnKeyPressed(keyEvent -> {
            int keyCode = keyEvent.getCode().getCode();
            String keyText = KeyEvent.getKeyText(keyCode);
            if (onlyKeyboard) {
                writeToFile("Tuş basıldı: " + keyText);
            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if (onlyMouse) {
                writeToFile("Fare tıklandı: X=" + x + ", Y=" + y);
            }
        });
    }

    private void writeToFile(String text) {
        try {
            writer.write(text);
            writer.newLine();
            writer.flush();
            if (logFile.length() > maxFileSize) {
                writer.close();
                fileWriter.close();
                logFile.delete();
                logFile.createNewFile();
                fileWriter = new FileWriter(logFile, true);
                writer = new BufferedWriter(fileWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isOnlyMouse() {
        return onlyMouse;
    }

    public void setOnlyMouse(boolean onlyMouse) {
        this.onlyMouse = onlyMouse;
        this.onlyKeyboard = !onlyMouse;
    }

    public boolean isOnlyKeyboard() {
        return onlyKeyboard;
    }

    public void setOnlyKeyboard(boolean onlyKeyboard) {
        this.onlyKeyboard = onlyKeyboard;
        this.onlyMouse = !onlyKeyboard;
    }

    public void setMouseAndKeyboard(boolean mouseAndKeyboard) {
        this.onlyKeyboard = mouseAndKeyboard;
        this.onlyMouse = mouseAndKeyboard;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize * 1024 * 1024;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
