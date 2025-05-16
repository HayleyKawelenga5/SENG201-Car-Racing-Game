package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.ShopService;
import seng201.team0.models.Upgrade;
import seng201.team0.services.UpgradeService;

import java.util.List;

public class ShopScreenController extends ScreenController {

    @FXML private Label moneyLabel;

    @FXML private Button sellUpgradeButton;
    @FXML private Button buyUpgradeButton;
    @FXML private Button sellCarButton;
    @FXML private Button buyCarButton;

    @FXML private Label upgradeSpeedLabel;
    @FXML private Label upgradeHandlingLabel;
    @FXML private Label upgradeReliabilityLabel;
    @FXML private Label upgradeFuelEconomyLabel;
    @FXML private Label upgradeCostLabel;

    @FXML private Button playerCar1Button;
    @FXML private Button playerCar2Button;
    @FXML private Button playerCar3Button;
    @FXML private Button playerCar4Button;
    @FXML private Button playerCar5Button;

    @FXML private Button shopCar1Button;
    @FXML private Button shopCar2Button;
    @FXML private Button shopCar3Button;

    @FXML private Button playerUpgrade1Button;
    @FXML private Button playerUpgrade2Button;
    @FXML private Button playerUpgrade3Button;

    @FXML private Button shopUpgrade1Button;
    @FXML private Button shopUpgrade2Button;
    @FXML private Button shopUpgrade3Button;

    @FXML private Label carSpeedLabel;
    @FXML private Label carHandlingLabel;
    @FXML private Label carReliabilityLabel;
    @FXML private Label carFuelEconomyLabel;
    @FXML private Label carCostLabel;

    @FXML private Button backButton;

    private int shopSelectedCarIndex = -1;
    private int shopSelectedUpgradeIndex = -1;

    private List<Car> availableCars;
    private List<Upgrade> availableUpgrades;

    private final CarService carService = new CarService();
    private final ShopService shopService = new ShopService();
    private final UpgradeService upgradeService = new UpgradeService();

    private List<Car> playerCars;
    private List<Upgrade> playerUpgrades;

    private Car selectedCar;
    private Upgrade selectedUpgrade;

    public ShopScreenController(GameManager manager) {
        super(manager);
    }

    @Override
    protected String getFxmlFile() {return "/fxml/shop.fxml";}

    @Override
    protected String getTitle() {return "Shop Screen";}

    public void initialize() {
        GameManager shopScreen = getGameManager();

        playerCars = shopScreen.getPlayerCars();
        playerUpgrades = shopScreen.getPlayerUpgrades();

        buyCarButton.setOnAction(event -> onBuyCarButtonClicked());
        sellCarButton.setOnAction(event -> onSellCarButtonClicked());
        buyUpgradeButton.setOnAction(event -> onBuyUpgradeButtonClicked());
        sellUpgradeButton.setOnAction(event -> onSellUpgradeButtonClicked());
        backButton.setOnAction(event -> onBackButtonClicked());

        List<Button> playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button, playerCar4Button, playerCar5Button);
        List<Button> shopCarButtons = List.of(shopCar1Button, shopCar2Button, shopCar3Button);
        List<Button> playerUpgradeButtons = List.of(playerUpgrade1Button, playerUpgrade2Button, playerUpgrade3Button);
        List<Button> shopUpgradeButtons = List.of(shopUpgrade1Button, shopUpgrade2Button, shopUpgrade3Button);

        for (int i = 0; i < playerCarButtons.size(); i++) {
            int index = i;
            playerCarButtons.get(i).setOnAction(event -> onPlayerCarButtonClicked(index));
        }
        for (int i = 0; i < shopCarButtons.size(); i++) {
            int index = i;
            shopCarButtons.get(i).setOnAction(event -> onShopCarButtonClicked(index));
        }
        for (int i = 0; i < playerCars.size(); i++) {
            playerCarButtons.get(i).setText(playerCars.get(i).getCarName());
        }

        availableCars = carService.generateRandomCars(3);
        availableUpgrades = upgradeService.generateRandomUpgrades();

        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            int index = i;
            playerUpgradeButtons.get(i).setOnAction(event -> onPlayerUpgradeButtonClicked(index));
         }

        for (int i = 0; i < shopUpgradeButtons.size(); i++) {
            int index = i;
            shopUpgradeButtons.get(i).setOnAction(event -> onShopUpgradeButtonClicked(index));
        }

        for (int i = 0; i < availableUpgrades.size(); i++) {
            shopUpgradeButtons.get(i).setText(availableUpgrades.get(i).getUpgradeName());
        }
        updateMoneyLabel();
        updatePlayerCarButtons();
        updateShopCarButtons();
        updatePlayerUpgradeButtons();
        updateShopUpgradeButtons();
    }

    @FXML
    public void onShopCarButtonClicked(int index) {
        shopSelectedCarIndex = index;
        selectedCar = availableCars.get(index);
        updateCarStats(selectedCar);
    }

    @FXML
    public void onPlayerCarButtonClicked(int index) {
        selectedCar = playerCars.get(index);
        updateCarStats(selectedCar);
    }

    @FXML
    public void onShopUpgradeButtonClicked(int index) {
        shopSelectedUpgradeIndex = index;
        selectedUpgrade = availableUpgrades.get(index);
        updateUpgradeStats(selectedUpgrade);
    }

    @FXML
    public void onPlayerUpgradeButtonClicked(int index) {
        selectedUpgrade = playerUpgrades.get(index);
        updateUpgradeStats(selectedUpgrade);
    }

    @FXML
    public void onBuyCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("Error", "Please select a car to buy.");
            return;
        }
        if (selectedCar.getCarCost() > getGameManager().getMoney()) {
            showAlert("Error", "Insufficient funds to buy this car.");
            return;
        }
        if (playerCars.size() >= 5) {
            showAlert("Error", "Too many cars, please sell a car before buying this car.");
            return;
        }

        if (selectedCar != null) {
            boolean bought = shopService.buyCar(selectedCar, getGameManager());
            if (bought) {
                TextInputDialog nameDialog = new TextInputDialog();
                nameDialog.setTitle("Name Your Car!");
                nameDialog.setHeaderText("Optional: Give your car a name or press OK");
                nameDialog.setContentText("Car name:");

                nameDialog.showAndWait().ifPresentOrElse(
                name -> selectedCar.setCarName(name.isBlank() ? "Car " + (shopSelectedCarIndex + 1) : name),
                        () -> selectedCar.setCarName("Car " + (shopSelectedCarIndex + 1))
                );
                availableCars.set(shopSelectedCarIndex, null);
                updateMoneyLabel();
                updatePlayerCarButtons();
                updateShopCarButtons();
                updateCarStats(null);
            } else {
                showAlert("Purchase failed", "Not enough money or max cars reached.");
            }
        }
    }

    @FXML
    public void onSellCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("Error", "Please select a car to sell.");
            return;
        }
        if (playerCars.size() <= 1) {
            showAlert("Error", "You must have at least one car.");
            return;
        }

        boolean sold = shopService.sellCar(selectedCar, getGameManager());
        if (sold) {
            updateMoneyLabel();
            updatePlayerCarButtons();
            updateCarStats(null);
        } else {
            showAlert("Sale failed", "Car not found in your garage.");
        }
    }

    @FXML
    public void onBuyUpgradeButtonClicked() {
        if (selectedUpgrade == null) {
            showAlert("Error", "Please select an upgrade to buy.");
            return;
        }
        if (selectedUpgrade.getUpgradeCost() > getGameManager().getMoney()) {
            showAlert("Error", "Insufficient funds to buy this upgrade.");
            return;
        }
        if (playerUpgrades.size() >= 3) {
            showAlert("Error", "Too many upgrades, please sell an upgrade before buying this upgrade.");
            return;
        }

        if (selectedUpgrade != null) {
            boolean bought = shopService.buyUpgrade(selectedUpgrade, getGameManager());
            if (bought) {
                availableUpgrades.set(shopSelectedUpgradeIndex, null);
                updateMoneyLabel();
                updatePlayerUpgradeButtons();
                updateShopUpgradeButtons();
                updateUpgradeStats(null);
            } else {
                showAlert("Purchase failed", "Not enough money or max upgrades reached.");
            }
        }
    }

    @FXML
    public void onSellUpgradeButtonClicked() {
        if (selectedUpgrade == null) {
            showAlert("Error", "Please select an upgrade to sell.");
            return;
        }

        boolean sold = shopService.sellUpgrade(selectedUpgrade, getGameManager());
        if (sold) {
            updateMoneyLabel();
            updatePlayerUpgradeButtons();
            updateUpgradeStats(null);
        } else {
            showAlert("Sale failed", "Upgrade not found in your garage.");
        }
    }

    public void updateCarStats(Car car) {
        if (car == null) {
            carSpeedLabel.setText("Speed: ");
            carHandlingLabel.setText("Handling: ");
            carReliabilityLabel.setText("Reliability: ");
            carFuelEconomyLabel.setText("Fuel Economy: ");
            carCostLabel.setText("Cost: ");
        } else {
            carSpeedLabel.setText("Speed: " + car.getCarSpeed());
            carHandlingLabel.setText("Handling: " + car.getCarHandling());
            carReliabilityLabel.setText("Reliability: " + car.getCarReliability());
            carFuelEconomyLabel.setText("Fuel Economy: " + car.getCarFuelEconomy());
            if (playerCars.contains(car)) {
                carCostLabel.setText("Cost: $" + car.getCarCost() / 2);
            } else {
                carCostLabel.setText("Cost: $" + car.getCarCost());
            }
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
            upgradeSpeedLabel.setText("Speed: " + upgrade.getUpgradeSpeed());
            upgradeHandlingLabel.setText("Handling: " + upgrade.getUpgradeHandling());
            upgradeReliabilityLabel.setText("Reliability: " + upgrade.getUpgradeReliability());
            upgradeFuelEconomyLabel.setText("Fuel Economy: " + upgrade.getUpgradeFuelEconomy());
            if (playerUpgrades.contains(upgrade)) {
                upgradeCostLabel.setText("Cost: $" + upgrade.getUpgradeCost() / 2);
            } else {
                upgradeCostLabel.setText("Cost: $" + upgrade.getUpgradeCost());
            }
        }
    }

    private void updatePlayerCarButtons() {
        List<Button> playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button, playerCar4Button, playerCar5Button);
        for (int i = 0; i < playerCarButtons.size(); i++) {
            if (i < playerCars.size()) {
                playerCarButtons.get(i).setText(playerCars.get(i).getCarName());
            } else {
                playerCarButtons.get(i).setText("");
            }
        }
    }

    private void updateShopCarButtons() {
        List<Button> shopCarButtons = List.of(shopCar1Button, shopCar2Button, shopCar3Button);
        for (int i = 0; i < shopCarButtons.size(); i++) {
            if (i < availableCars.size() && availableCars.get(i) != null) {
                shopCarButtons.get(i).setText("Car " + (i + 1));
            } else {
                shopCarButtons.get(i).setText("");
            }
        }
    }

    private void updatePlayerUpgradeButtons() {
        List<Button> playerUpgradeButtons = List.of(playerUpgrade1Button, playerUpgrade2Button, playerUpgrade3Button);
        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            if (i < playerUpgrades.size()) {
                playerUpgradeButtons.get(i).setText(playerUpgrades.get(i).getUpgradeName());
            } else {
                playerUpgradeButtons.get(i).setText("");
            }
        }
    }

    private void updateShopUpgradeButtons() {
        List<Button> shopUpgradeButtons = List.of(shopUpgrade1Button, shopUpgrade2Button, shopUpgrade3Button);
        for (int i = 0; i < shopUpgradeButtons.size(); i++) {
            if (i < availableUpgrades.size() && availableUpgrades.get(i) != null) {
                shopUpgradeButtons.get(i).setText(availableUpgrades.get(i).getUpgradeName());
            } else {
                shopUpgradeButtons.get(i).setText("");
            }
        }
    }

    @FXML
    public void onBackButtonClicked() {
        getGameManager().setPlayerCars(playerCars);
        getGameManager().setPlayerUpgrades(playerUpgrades);
        getGameManager().goBack();
    }

    public void updateMoneyLabel(){
        moneyLabel.setText("Money: $" + getGameManager().getMoney());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

