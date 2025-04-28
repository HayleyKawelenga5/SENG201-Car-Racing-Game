package seng201.team0.gui;

import javafx.scene.layout.GridPane;
import seng201.team0.GameManager;
import seng201.team0.services.GameInitialiser;

public class MainScreenController extends ScreenController {

    public MainScreenController(GameManager manager) {super(manager);}

    @Override
    protected String getFxmlFile() {return "/fxml/main_screen.fxml";}

    @Override
    protected String getTitle() {return "Main Screen";}
}
