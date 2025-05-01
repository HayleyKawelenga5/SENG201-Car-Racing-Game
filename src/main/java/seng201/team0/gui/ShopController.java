package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.ShopService;
import seng201.team0.services.UpgradeService;
import seng201.team0.models.Upgrade;

import java.util.List;

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

    @FXML private Button myUpgrade1Button;
    @FXML private Button myUpgrade2Button;
    @FXML private Button myUpgrade3Button;

    @FXML private Button shopUpgrade1Button;
    @FXML private Button shopUpgrade2Button;
    @FXML private Button shopUpgrade3Button;

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
    private Button backButton;

    private int mySelectedCarIndex = -1;
    private int shopSelectedCarIndex = -1;

    private List<Car> shopCars;
    private CarService carService;
    private ShopService shopService;
    private UpgradeService upgradeService = new UpgradeService();
    private Car selectedCar;
    private Upgrade selectedUpgrade;
    private List<Car> selectedCars;
    private List<Upgrade> availableUpgrades;


    public ShopController(GameManager manager) {
        super(manager);
        this.shopService = new ShopService();
        this.carService = new CarService();
        }

    @Override
    protected String getFxmlFile() {return "/fxml/shop.fxml";}

    @Override
    protected String getTitle() {return "Shop";}

    public void initialize(){
        GameManager gameManager = getGameManager();
        int playerMoney = gameManager.getPlayerMoney();
        selectedCars = gameManager.getSelectedCars();

        backButton.setOnAction(event -> onBackButtonClicked());

        moneyLabel.setText("Money: $" + playerMoney); //change this to use helper method

        List<Button> myCarButtons = List.of(myCarButton1, myCarButton2, myCarButton3, myCarButton4, myCarButton5);
        List<Button> shopCarButtons = List.of(shopCarButton1, shopCarButton2, shopCarButton3);
        List<Button> myUpgradeButtons = List.of(myUpgrade1Button, myUpgrade2Button, myUpgrade3Button);
        List<Button> shopUpgradeButtons = List.of(shopUpgrade1Button, shopUpgrade2Button, shopUpgrade3Button);

        for (int i = 0; i < myCarButtons.size(); i++){
            int index = i;
            myCarButtons.get(i).setOnAction(event -> onMyCarButtonClicked(myCarButtons, index));
        }
        for (int i = 0; i < shopCarButtons.size(); i++) {
            int index = i;
            shopCarButtons.get(i).setOnAction(event -> onShopCarButtonClicked(shopCarButtons, index));
        }
        for (int i = 0; i < selectedCars.size(); i++) {
            myCarButtons.get(i).setText(selectedCars.get(i).getName());
        }
        shopCars = carService.generateRandomCars(3);
        /**
        for (int i = 0; i < myUpgradeButtons.size(); i++) {
            int index = i;
            myUpgradeButtons.get(i).setOnAction(event -> onMyUpgradeButtonClicked(myCarButtons, index));
        }
        */
        for (int i = 0; i < shopUpgradeButtons.size(); i++) {
            int index = i;
            shopUpgradeButtons.get(i).setOnAction(event -> onShopUpgradeButtonClicked(myCarButtons, index));
        }

        availableUpgrades = upgradeService.generateRandomUpgrades();

        for (int i = 0; i < availableUpgrades.size(); i++) {
            shopUpgradeButtons.get(i).setText(availableUpgrades.get(i).getName());
        }
    }

    @FXML
    public void onShopCarButtonClicked(List<Button> shopCarButtons, int index) {
        shopSelectedCarIndex = index;
        selectedCar = shopCars.get(index);
        updateCarStats(selectedCar);
    }

    @FXML
    public void onMyCarButtonClicked(List<Button> myCarButtons, int index) {
        mySelectedCarIndex = index;
        selectedCar = selectedCars.get(index);
        updateCarStats(selectedCar);
    }

    @FXML
    public void onShopUpgradeButtonClicked(List<Button> myUpgradeButtons, int index) {
        selectedUpgrade = availableUpgrades.get(index);
        updateUpgradeStats(selectedUpgrade);
    }

    @FXML
    private void onSellCarButtonClicked(){
        if (selectedCar != null && selectedCars.contains(selectedCar)){
            boolean sold = shopService.sellCar(selectedCar);
            if (sold){
                selectedCars.remove(selectedCar);
                updateMoneyLabel();
                updateMyCarButtons();
                updateCarStats(null);
            } else {
                //car could not be sold error
            }
        } else {
            //no valid car selected er}
        }
    }

    public void updateCarStats(Car car){
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
            carCostLabel.setText("Cost: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getSpeed());
            carHandlingLabel.setText("Handling: " + car.getHandling());
            carReliabilityLabel.setText("Reliability: " + car.getReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getFuelEconomy());
            if (selectedCars.contains(car)){
            carCostLabel.setText("Cost: $" + car.getCost()/2);}
            else {carCostLabel.setText("Cost: $" + car.getCost());}
        }
    }

    private void updateUpgradeStats(Upgrade upgrade) {
        if (upgrade == null) {
            upgradeSpeedLabel.setText("Speed: ");
            upgradeHandlingLabel.setText("Handling: ");
            upgradeReliabilityLabel.setText("Reliability: ");
            upgradeFuelEconomyLabel.setText("Fuel Economy: ");
            upgradeCostLabel.setText("Cost: ");
        } else {
            upgradeSpeedLabel.setText("Speed: " + upgrade.getSpeedUpgrade());
            upgradeHandlingLabel.setText("Handling: " + upgrade.getHandlingUpgrade());
            upgradeReliabilityLabel.setText("Reliability: " + upgrade.getReliabilityUpgrade());
            upgradeFuelEconomyLabel.setText("Fuel Economy: " + upgrade.getFuelEconomyUpgrade());
            upgradeCostLabel.setText("Cost: $" + upgrade.getCost());
        }
    }

    private void updateMyCarButtons(){
        List<Button> myCarButtons = List.of(myCarButton1, myCarButton2, myCarButton3, myCarButton4, myCarButton5);
        for (int i = 0; i < myCarButtons.size(); i++) {
            if (i < selectedCars.size()) {
                myCarButtons.get(i).setText(selectedCars.get(i).getName());
            } else {
                myCarButtons.get(i).setText("");
            }
        }
    }


    @FXML
    public void onBackButtonClicked() {
        getGameManager().goBack();
    }


    public void updateMoneyLabel(){
        moneyLabel.setText("Money: $" + getGameManager().getPlayerMoney());
    }
}
