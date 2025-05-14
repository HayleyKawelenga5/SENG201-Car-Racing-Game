package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.GameManager;

public class FinishScreenController extends ScreenController {

    @FXML
    private Label playerNameLabel;
    @FXML
    private Label seasonLengthLabel;
    @FXML
    private Label playerRacesCompetedLabel;
    @FXML
    private Label playerAveragePlacingLabel;
    @FXML
    private Label playerPrizeMoneyLabel;

    private String playerName;
    private int seasonLength;
    private int playerRacesCompeted;
    private double playerAveragePlacing;
    private int playerPrizeMoney;

    public FinishScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/finish_screen.fxml";}

    @Override
    protected String getTitle() {return "Finish Screen";}

    public void initialize() {
        GameManager finishScreen = getGameManager();
        playerName = finishScreen.getPlayerName();
        seasonLength = finishScreen.getSeasonLength();
        playerRacesCompeted = finishScreen.getSeasonLength() - finishScreen.getRacesRemaining();
        playerAveragePlacing = finishScreen.getAveragePlayerFinishPositions();
        playerPrizeMoney = finishScreen.getPlayerTotalPrizeMoney();

        playerNameLabel.setText("Player Name : " + finishScreen.getPlayerName());
        seasonLengthLabel.setText("Season Length : " + Integer.toString(seasonLength));
        playerRacesCompetedLabel.setText("Races Competed : " + Integer.toString(playerRacesCompeted));
        playerAveragePlacingLabel.setText("Average Placing : " + Double.toString(playerAveragePlacing));
        playerPrizeMoneyLabel.setText("Total Prize Money : " + Integer.toString(playerPrizeMoney));
    }
}
