package seng201.team0.gui;

import seng201.team0.GameManager;

/**
 * Abstract parent class for all {@link GameManager} UI controller classes.
 */

public abstract class ScreenController {

    private final GameManager gameManager;

    /**
     * Creates an instant of ScreenController with the given {@link GameManager}
     * @param gameManager The game manager used by this ScreenController
     */
    protected ScreenController(final GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     * @return The full path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the game manager associated with this screen controller
     * @return the game manager for this controller
     */
    protected GameManager getGameManager(){
        return gameManager;
    }
}


