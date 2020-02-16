package com.semicolon.ds.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/first.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        window.setOnCloseRequest(event -> {
            event.consume();
            controller.leaveAction();
        });
        primaryStage.setTitle("Distributed File Sharing");
        primaryStage.setScene(new Scene(root, 1088, 594));
        primaryStage.show();

    }


}