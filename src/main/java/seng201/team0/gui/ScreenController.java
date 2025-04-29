package seng201.team0.gui;

import seng201.team0.GameManager;

/**
 *
 */

public abstract class ScreenController {

    private final GameManager gameManager;


    protected ScreenController(final GameManager gameManager) {
        this.gameManager = gameManager;
    }


    protected abstract String getFxmlFile();


    protected abstract String getTitle();

    protected GameManager getGameManager(){
        return gameManager;
    }
}


