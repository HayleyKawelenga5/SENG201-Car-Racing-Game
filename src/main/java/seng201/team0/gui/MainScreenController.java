package seng201.team0.gui;

import javafx.scene.layout.GridPane;
import seng201.team0.GameManager;
import seng201.team0.services.GameInitialiser;

public class MainScreenController extends ScreenController {
    @FXML private Button race1Button;
    @FXML private Button race2Button;
    @FXML private Button race3Button;

    @FXML private Button toShopButton;
    @FXML private Button toGarageButton;
    @FXML private Button selectRaceButton;
    @FXML private Button startRaceButton;

    @FXML private Label currentCarNameLabel;
    @FXML private Label currentCarSpeedLabel;
    @FXML private Label currentCarHandlingLabel;
    @FXML private Label currentCarReliabilityLabel;
    @FXML private Label currentCarFuelEconomyLabel;

    @FXML private Label seasonLengthLabel;
    @FXML private Label racesRemianingLabel;
    @FXML private Label moneyLabel;

    @FXML private Label raceHoursLabel;
    @FXML private Label raceRoutesLabel;
    @FXML private Label raceEntriesLabel;
    @FXML private Label racePrizeMoneyLabel;

    @FXML private Label selectedRaceLabel;

    public MainScreenController(GameManager manager) {super(manager);}

    @Override
    protected String getFxmlFile() {return "/fxml/main_screen.fxml";}

    @Override
    protected String getTitle() {return "Main Screen";}
}
