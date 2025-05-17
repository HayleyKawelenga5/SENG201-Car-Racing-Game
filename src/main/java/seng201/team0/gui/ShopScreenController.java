package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import seng201.team0.GameManager;

import seng201.team0.models.Car;
import seng201.team0.services.CarService;
import seng201.team0.services.ShopService;
import seng201.team0.models.Upgrade;
import seng201.team0.services.UpgradeService;
import seng201.team0.utils.ScreenUpdater;

import java.util.List;

/**
 * Controller class for the Shop Screen GUI.
 *<p></p>
 * This controller handles user interactions for buying and selling cars and upgrades, viewing the amount of money the
 * user has and viewing cars and upgrades/tuning parts available for purchase. It updates the GUI components based on the
 * player's selections and game state, and communicated with the {@link ShopService} and {@link GameManager} to manage
 * the logic for transactions and display updates
 */
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

    private List<Button> playerCarButtons;
    private List<Button> playerUpgradeButtons;
    private List<Button> shopCarButtons;
    private List<Button> shopUpgradeButtons;

    /**
     * Constructs a ShopScreenController with the given {@link GameManager}
     * @param manager The GameManager managing game state.
     */
    public ShopScreenController(GameManager manager) {
        super(manager);
    }

    /**
     * Returns the path to the FXML file for this screen.
     * @return Path to FXML file.
     */
    @Override
    protected String getFxmlFile() {return "/fxml/shop.fxml";}

    /**
     * Returns the window title for this screen.
     * @return Title of the shop screen.
     */
    @Override
    protected String getTitle() {return "Shop Screen";}

    /**
     * Initializes the Shop screen controller.
     * Sets up button listeners, loads available cars and upgrades and update UI elements.
     * This method is immediately called by the JavaFX framework after FXML loading.
     */
    @FXML
    private void initialize() {
        GameManager gameManager = getGameManager();

        playerCars = gameManager.getPlayerCars();
        playerUpgrades = gameManager.getPlayerUpgrades();

        buyCarButton.setOnAction(event -> onBuyCarButtonClicked());
        sellCarButton.setOnAction(event -> onSellCarButtonClicked());
        buyUpgradeButton.setOnAction(event -> onBuyUpgradeButtonClicked());
        sellUpgradeButton.setOnAction(event -> onSellUpgradeButtonClicked());
        backButton.setOnAction(event -> onBackButtonClicked());

        playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button, playerCar4Button, playerCar5Button);
        shopCarButtons = List.of(shopCar1Button, shopCar2Button, shopCar3Button);
        playerUpgradeButtons = List.of(playerUpgrade1Button, playerUpgrade2Button, playerUpgrade3Button);
        shopUpgradeButtons = List.of(shopUpgrade1Button, shopUpgrade2Button, shopUpgrade3Button);

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

    /**
     * Called when a shop car button is clicked i.e a car available for purchase
     * Selects the car at the given index and updates the car stats display.
     *
     * @param index the index of the selected shop car
     */
    @FXML
    private void onShopCarButtonClicked(int index) {
        shopSelectedCarIndex = index;
        selectedCar = availableCars.get(index);
        updateCarStats(selectedCar);
    }

    /**
     * Called when a player car button is clicked i.e a car owned by the player.
     * Selects the player's car at the given index and updates the car stats display.
     *
     * @param index the index of the selected player car
     */
    @FXML
    private void onPlayerCarButtonClicked(int index) {
        selectedCar = playerCars.get(index);
        updateCarStats(selectedCar);
    }

    /**
     * Called when a shop upgrade button is clicked i.e. an upgrade available for purchase
     * Selects the upgrade at the given index and updates the upgrade stats display.
     *
     * @param index the index of the selected shop upgrade
     */
    @FXML
    private void onShopUpgradeButtonClicked(int index) {
        shopSelectedUpgradeIndex = index;
        selectedUpgrade = availableUpgrades.get(index);
        updateUpgradeStats(selectedUpgrade);
    }

    /**
     * Called when a player upgrade button is clicked i.e. an upgrade owned by the player.
     * Selects the player's upgrade at the given index and updates the upgrade stats display.
     *
     * @param index the index of the selected player upgrade
     */
    @FXML
    private void onPlayerUpgradeButtonClicked(int index) {
        selectedUpgrade = playerUpgrades.get(index);
        updateUpgradeStats(selectedUpgrade);
    }

    /**
     * Handles the event when the buy car button is clicked.
     * Validates selection, cost, and capacity before purchasing a car. Ensures the user does not exceed the maximum
     * number of cars and the user has enough money before buying a car.
     */
    @FXML
    private void onBuyCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("Error", "Please select a car to buy.");
            return;
        }
        if (selectedCar.getCarCost() > getGameManager().getMoney()) {
            showAlert("Error", "Insufficient funds to buy this car.");
            return;
        }
        if (playerCars.size() >= shopService.getMaxMoney()) {
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

    /**
     * Handles the event when the sell car button is clicked.
     * Validates selection and ensures the player keeps at least one car before selling.
     */
    @FXML
    private void onSellCarButtonClicked() {
        if (selectedCar == null) {
            showAlert("Error", "Please select a car to sell.");
            return;
        }
        if (playerCars.size() <= shopService.getMinCars()) {
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

    /**
     * Handles the event when the buy upgrade button is clicked.
     * Validates selection, cost, and capacity before purchasing an upgrade. Ensures the user has enough money to buy
     * the selected upgrade.
     */
    @FXML
    private void onBuyUpgradeButtonClicked() {
        if (selectedUpgrade == null) {
            showAlert("Error", "Please select an upgrade to buy.");
            return;
        }
        if (selectedUpgrade.getUpgradeCost() > getGameManager().getMoney()) {
            showAlert("Error", "Insufficient funds to buy this upgrade.");
            return;
        }
        if (playerUpgrades.size() >= shopService.getMaxUpgrades()) {
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

    /**
     * Handles the event when the sell upgrade button is clicked.
     * Validates selection before removing the upgrade from the player's collection.
     */
    @FXML
    private void onSellUpgradeButtonClicked() {
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

    /**
     * Updates the car stats labels with the stats of the selected car.
     * Also updates the cost label depending on whether the car is owned by the player or not.
     *
     * @param car the car whose stats should be displayed, or null to clear the display
     */
    private void updateCarStats(Car car) {
        ScreenUpdater.updateCarStats(car, carSpeedLabel, carHandlingLabel, carReliabilityLabel, carFuelEconomyLabel);
        if (car == null){
            carCostLabel.setText("Cost: ");
        } else if (playerCars.contains(car)) {
            carCostLabel.setText("Cost: $" + car.getCarCost() / 2);
        } else{
            carCostLabel.setText("Cost: $" + car.getCarCost());
        }
    }

    /**
     * Updates the upgrade stats panel with the stats of the selected upgrade.
     * Also updates the cost label depending on whether the upgrade is owned by the player or not.
     *
     * @param upgrade the upgrade whose stats should be displayed, or null to clear the display
     */
    private void updateUpgradeStats(Upgrade upgrade) {
        ScreenUpdater.updateUpgradeStats(upgrade, upgradeSpeedLabel, upgradeHandlingLabel, upgradeReliabilityLabel, upgradeFuelEconomyLabel);
        if (upgrade == null){
            upgradeCostLabel.setText("Cost: ");
        } else if (playerUpgrades.contains(upgrade)) {
            upgradeCostLabel.setText("Cost: $" + upgrade.getUpgradeCost() / 2);
        } else {
            upgradeCostLabel.setText("Cost: $" + upgrade.getUpgradeCost());
        }
    }

    /**
     * Updates the player's car button labels based on current owned cars.
     * Clears buttons that are not in use.
     */
    private void updatePlayerCarButtons() {
        playerCarButtons = List.of(playerCar1Button, playerCar2Button, playerCar3Button, playerCar4Button, playerCar5Button);
        for (int i = 0; i < playerCarButtons.size(); i++) {
            if (i < playerCars.size()) {
                playerCarButtons.get(i).setText(playerCars.get(i).getCarName());
            } else {
                playerCarButtons.get(i).setText("");
            }
        }
    }

    /**
     * Updates the shop car button labels based on currently available cars.
     * Clears buttons that are not in use.
     */
    private void updateShopCarButtons() {
        shopCarButtons = List.of(shopCar1Button, shopCar2Button, shopCar3Button);
        for (int i = 0; i < shopCarButtons.size(); i++) {
            if (i < availableCars.size() && availableCars.get(i) != null) {
                shopCarButtons.get(i).setText("Car " + (i + 1));
            } else {
                shopCarButtons.get(i).setText("");
            }
        }
    }

    /**
     * Updates the player's upgrade button labels based on current owned upgrades.
     * Clears buttons that are not in use.
     */
    private void updatePlayerUpgradeButtons() {
        playerUpgradeButtons = List.of(playerUpgrade1Button, playerUpgrade2Button, playerUpgrade3Button);
        for (int i = 0; i < playerUpgradeButtons.size(); i++) {
            if (i < playerUpgrades.size()) {
                playerUpgradeButtons.get(i).setText(playerUpgrades.get(i).getUpgradeName());
            } else {
                playerUpgradeButtons.get(i).setText("");
            }
        }
    }

    /**
     * Updates the shop upgrade button labels based on available upgrades.
     * Clears buttons that are not in use.
     */
    private void updateShopUpgradeButtons() {
        shopUpgradeButtons = List.of(shopUpgrade1Button, shopUpgrade2Button, shopUpgrade3Button);
        for (int i = 0; i < shopUpgradeButtons.size(); i++) {
            if (i < availableUpgrades.size() && availableUpgrades.get(i) != null) {
                shopUpgradeButtons.get(i).setText(availableUpgrades.get(i).getUpgradeName());
            } else {
                shopUpgradeButtons.get(i).setText("");
            }
        }
    }

    /**
     * Returns to the previous screen, saving the player's cars and upgrades back to the game manager.
     */
    @FXML
    private void onBackButtonClicked() {
        getGameManager().setPlayerCars(playerCars);
        getGameManager().setPlayerUpgrades(playerUpgrades);
        getGameManager().goBack();
    }


    /**
     * Updates the label showing the player's current money.
     */
    private void updateMoneyLabel(){
        moneyLabel.setText("Money: $" + getGameManager().getMoney());
    }

    /**
     * Displays an alert dialog with a given title and message.
     *
     * @param title   the title of the alert window
     * @param message the message content of the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

