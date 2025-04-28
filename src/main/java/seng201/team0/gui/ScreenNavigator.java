package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seng201.team0.GameManager;

import java.io.IOException;

public class ScreenNavigator {

    private final Stage stage;

    private final BorderPane rootPane;

    public ScreenNavigator(Stage stage) {
        this.stage = stage;

        rootPane = new BorderPane();
        rootPane.setPrefHeight(400);
        rootPane.setPrefWidth(600);
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    public void launchGameInitialiserScreen(GameManager gameManager) {
        ScreenController controller = new GameInitialiserController(gameManager);
        launchScreen(controller);
    }

    public void launchMainScreen(GameManager gameManager) {
        ScreenController controller = new MainScreenController(gameManager);
        launchScreen(controller);
    }

    private void launchScreen(ScreenController controller) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource(controller.getFxmlFile()));
            // Set a controller factory that returns the given ScreenController.
            // This allows us to have screen controllers that take argument(s) in their constructor.
            setupLoader.setControllerFactory(param -> controller);
            Parent setupParent  = setupLoader.load();
            rootPane.setCenter(setupParent);
            stage.setTitle(controller.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
