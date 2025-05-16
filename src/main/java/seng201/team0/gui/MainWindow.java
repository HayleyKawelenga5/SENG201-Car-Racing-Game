package seng201.team0.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import seng201.team0.GameManager;

/**
 * Class starts the javaFX application thread
 * @author seng201 teaching team
 */
public class MainWindow extends Application {

    /**
     * Creates the {@link GameManager} with a {@link ScreenNavigator} for the given {@link Stage}
     * This method includes code from labs/tutorials.
     * @param primaryStage The current fxml stage, handled by this JavaFX Application class
     */
    @Override
    public void start(Stage primaryStage){
        new GameManager(new ScreenNavigator(primaryStage));
    }
}
