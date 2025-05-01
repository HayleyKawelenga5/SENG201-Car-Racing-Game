package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seng201.team0.GameManager;

import java.io.IOException;

/**
 * Class that handles navigation between various {@link ScreenController}s. This navigator uses
 * a {@link BorderPane} layout for the root pane. The launched screen is placed in the center
 * area of the border bane, replacing the previous screen.
 */
public class ScreenNavigator {

    private final Stage stage;

    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     * @param stage The JavaFX stage
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
     * Launches the game initialiser screen
     * @param gameManager The game manager used by the game initialiser screen controller
     */

    public void launchGameInitialiserScreen(GameManager gameManager) {
        ScreenController controller = new GameInitialiserController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the main screen
     * @param gameManager The game manager used by the main screen controller
     */

    public void launchMainScreen(GameManager gameManager) {
        ScreenController controller = new MainScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the garage screen
     * @param gameManager The game manager used by the garage screen controller
     */
    public void launchGarageScreen(GameManager gameManager) {
        ScreenController controller = new GarageScreenController(gameManager);
        launchScreen(controller);
    }

    /**
     * Launches the shop screen
     * @param gameManager The game manager used by the shop screen controller
     */
    public void launchShopScreen(GameManager gameManager) {
        ScreenController controller = new ShopController(gameManager);
        launchScreen(controller);
    }

    /**
     * Replaces the root border pane's center component with the screen defined by the given {@link ScreenController}
     * @param controller The JavaFX screen controller for the screen to be launched
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
            e.printStackTrace();
        }
    }
}
