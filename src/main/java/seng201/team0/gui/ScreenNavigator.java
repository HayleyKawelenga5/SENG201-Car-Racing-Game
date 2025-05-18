package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import seng201.team0.GameManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles navigation between various {@link ScreenController}s. This navigator uses
 * a {@link BorderPane} layout for the root pane. The launched screen is placed in the center
 * area of the border bane, replacing the previous screen. Code from the labs and/or tutorials has been used in this class.
 */
public class ScreenNavigator {
    /**
     * Logger instance for logging messages related to the ScreenNavigator class.
     */
    private static final Logger logger = Logger.getLogger(ScreenNavigator.class.getName());

    /**
     * The primary JavaFX Stage (window) used for displaying scenes.
     */
    private final Stage stage;

    /**
     * The root BorderPane layout that holds the main UI components for the current scene.
     */
    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     * @param stage The JavaFX stage (Stage)
     */
    public ScreenNavigator(Stage stage) {
        this.stage = stage;

        rootPane = new BorderPane();
        rootPane.setPrefHeight(400);
        rootPane.setPrefWidth(600);
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    /**
     * Launches the start screen
     * @param gameManager The game manager used by the start screen controller (GameManager)
     */
    public void launchStartScreen(GameManager gameManager) {
        ScreenController controller = new StartScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the main screen
     * @param gameManager The game manager used by the main screen controller (GameManager)
     */
    public void launchMainScreen(GameManager gameManager) {
        ScreenController controller = new MainScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the garage screen
     * @param gameManager The game manager used by the garage screen controller (GameManager)
     */
    public void launchGarageScreen(GameManager gameManager) {
        ScreenController controller = new GarageScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the shop screen
     * @param gameManager The game manager used by the shop screen controller (GameManager)
     */
    public void launchShopScreen(GameManager gameManager) {
        ScreenController controller = new ShopScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the race screen
     * @param gameManager The game manager used by the shop screen controller (GameManager)
     */
    public void launchRaceScreen(GameManager gameManager) {
        ScreenController controller = new RaceScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the finish screen
     * @param gameManager The game manager used by the shop screen controller (GameManager)
     */
    public void launchFinishScreen(GameManager gameManager) {
        ScreenController controller = new FinishScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Replaces the root border pane's center component with the screen defined by the given {@link ScreenController}
     * @param controller The JavaFX screen controller for the screen to be launched (ScreenController)
     */
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
            logger.log(Level.SEVERE, "Failed to load FXML for " + controller.getFxmlFile(), e);
        }
    }
}
