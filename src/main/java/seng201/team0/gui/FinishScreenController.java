package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameManager;

/**
 * Controller for the Finish Screen of the game.
 * this screen is shown when the game ends and displays the final player statistics,
 * including name, season length, races completed, average placing, and total prize money.
 */
public class FinishScreenController extends ScreenController {

    @FXML private Label playerNameLabel;
    @FXML private Label seasonLengthLabel;
    @FXML private Label playerRacesCompetedLabel;
    @FXML private Label playerAveragePlacingLabel;
    @FXML private Label playerPrizeMoneyLabel;
    @FXML private Label playerTotalMoneyLabel;
    @FXML private Button quitButton;

    /**
     * Constructs a FinishScreenController with the given {@link GameManager}.
     * @param manager The GameManager managing game state. (GameManager)
     */
    public FinishScreenController(GameManager manager) {
        super(manager);
    }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {return "/fxml/finish_screen.fxml";}

    /**
     * Returns the window title for this screen.
     * @return Title of the finish screen.
     */
    @Override
    protected String getTitle() {return "Finish Screen";}

    /**
     *Initializes the finish screen with data from the GameManager.
     *Populates all GUI labels with final player statistics.
     */
    @FXML
    private void initialize() {
        GameManager finishScreen = getGameManager();

        String playerName = finishScreen.getPlayerName();
        int seasonLength = finishScreen.getSeasonLength();
        int playerRacesCompeted = finishScreen.getSeasonLength() - (getGameManager().getRacesRemaining()-1);
        double playerAveragePlacing = finishScreen.getAveragePlayerFinishPositions();
        int playerPrizeMoney = finishScreen.getPlayerTotalPrizeMoney();
        int playerTotalMoney = finishScreen.getMoney() + playerPrizeMoney;

        playerNameLabel.setText("Player Name : " + playerName);
        seasonLengthLabel.setText("Season Length : " + seasonLength);
        playerRacesCompetedLabel.setText("Races Competed : " + playerRacesCompeted);
        playerAveragePlacingLabel.setText("Average Placing : " + playerAveragePlacing);
        playerPrizeMoneyLabel.setText("Total Prize Money : $" + playerPrizeMoney);
        playerTotalMoneyLabel.setText("Total Money : $" + playerTotalMoney);

        quitButton.setOnAction(event -> onQuitButtonClicked());
    }

    /**
     * Handles the event when the "Quit" button is clicked on the finish screen.
     */
    @FXML
    private void onQuitButtonClicked() {
        getGameManager().onQuitRequested();
    }
}
