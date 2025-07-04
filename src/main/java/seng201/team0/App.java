package seng201.team0;

import seng201.team0.gui.MainWindow;

/**
 * Default entry point class. This contains code from the labs and/or tutorials.
 * @author seng201 teaching team
 */
public class App {

    /**
     * Entry point which runs the javaFX application
     * Due to how JavaFX works we must call MainWindow.launch() from here,
     * trying to run MainWindow itself will cause an error
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        MainWindow.launch(MainWindow.class, args);
    }
}
