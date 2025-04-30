package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameManager;

public class ShopController extends ScreenController{

    @FXML
    private Label moneyLabel;
    @FXML
    private Button sellUpgradeButton;
    @FXML
    private Button buyUpgradeButton;

    @FXML
    private Button myUpgradeButton1;
    @FXML
    private Button myUpgradeButton2;
    @FXML
    private Button myUpgradeButton3;

    @FXML
    private Label upgradeSpeedLabel;
    @FXML
    private Label upgradeHandlingLabel;
    @FXML
    private Label upgradeReliabilityLabel;
    @FXML
    private Label upgradeFuelEconomyLabel;
    @FXML
    private Label upgradeCostLabel;

    @FXML
    private Button sellCarButton;
    @FXML
    private Button buyCarButton;

    @FXML
    private Button myCarButton1;
    @FXML
    private Button myCarButton2;
    @FXML
    private Button myCarButton3;
    @FXML
    private Button myCarButton4;
    @FXML
    private Button myCarButton5;

    @FXML
    private Button shopCarButton1;
    @FXML
    private Button shopCarButton2;
    @FXML
    private Button shopCarButton3;

    @FXML
    private Label carSpeedLabel;
    @FXML
    private Label carHandlingLabel;
    @FXML
    private Label carReliabilityLabel;
    @FXML
    private Label carFuelEconomyLabel;
    @FXML
    private Label carCostLabel;

    @FXML
    private Button toMainScreenButton;

    private GameManager gameManager;

    public ShopController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/shop.fxml";}

    @Override
    protected String getTitle() {return "Shop";}

    public void Initialize(){
        GameManager gameManager = getGameManager();
        int playerMoney = gameManager.getPlayerMoney();

        moneyLabel.setText("Money: $" + playerMoney);

    }

}
