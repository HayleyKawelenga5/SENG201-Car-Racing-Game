package seng201.team0.gui;

import javafx.scene.layout.GridPane;
import seng201.team0.services.GameInitialiser;

public class MainScreenController {
    private GameInitialiser game;
    private GridPane mainGridPane;

    public void setGame(GameInitialiser game) {
        this.game = game;
    }
}
