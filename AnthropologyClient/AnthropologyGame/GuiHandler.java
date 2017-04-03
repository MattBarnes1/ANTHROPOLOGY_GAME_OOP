/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.FXML.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class GuiHandler {
    private String ChoiceBody;
    private Stage currentStage;
    private Scene pauseMenu;
    private PauseController pauseController;
    private Scene mainMenu;
    private MainMenuController mainMenuController;
    private Scene loadScreen;
    private LoadScreenController loadScreenController;
    private  Scene displayChoice;
    private DisplayChoiceController displayChoiceController;
    private Scene explainChoice;
    private ExplainChoiceController explainChoiceController;
    private Scene mainGameScreen;
    private MainGameScreenController mainGameScreenController;
    private Scene socialValuesSelectionScreen;
    private SocialValuesSelectionController socialValuesSelectionController;
    private Scene mapLocationSelectionScreen;
    private MapLocationSelectionController mapLocationSelectionController;
    private Scene saveScreen;
    private SaveScreenController saveScreenController;
    
    public GuiHandler(Stage aStage, int WindowWidth, int WindowHeight)
    {
        currentStage = aStage;
        
        aStage.setTitle("Tribal!");
        FXMLLoader pauseMenuLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/pause.fxml"));
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/mainMenu.fxml"));
        FXMLLoader loadScreenLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/loadScreen.fxml"));
        FXMLLoader displayChoiceLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/displayChoice.fxml"));
        FXMLLoader explainChoiceLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/explainChoice.fxml"));
        FXMLLoader mainGameScreenLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/mainGameScreen.fxml"));
        FXMLLoader socialValuesSelectionScreenLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/socialValuesSelection.fxml"));
        FXMLLoader mapLocationSelectionScreenLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/mapLocationSelection.fxml"));
        FXMLLoader saveScreenLoader = new FXMLLoader(getClass().getResource("/anthropologyapplication/FXML/saveScreen.fxml"));
        
        try {
                pauseMenu = new Scene(pauseMenuLoader.load(), WindowWidth, WindowHeight);
                mainMenu = new Scene(mainMenuLoader.load(), WindowWidth, WindowHeight);
                loadScreen = new Scene(loadScreenLoader.load(), WindowWidth, WindowHeight);
                saveScreen = new Scene(saveScreenLoader.load(), WindowWidth, WindowHeight);
                displayChoice = new Scene(displayChoiceLoader.load(), WindowWidth, WindowHeight);
                explainChoice = new Scene(explainChoiceLoader.load(), WindowWidth, WindowHeight);
                mainGameScreen = new Scene(mainGameScreenLoader.load(), WindowWidth, WindowHeight);
                socialValuesSelectionScreen = new Scene(socialValuesSelectionScreenLoader.load(), WindowWidth, WindowHeight);
                mapLocationSelectionScreen = new Scene(mapLocationSelectionScreenLoader.load(), WindowWidth, WindowHeight);
            } catch (IOException ex) {
                Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        socialValuesSelectionController = socialValuesSelectionScreenLoader.getController();
        mapLocationSelectionController = mapLocationSelectionScreenLoader.getController();
        pauseController = pauseMenuLoader.getController();
        mainMenuController = mainMenuLoader.getController();
        loadScreenController = loadScreenLoader.getController();
        displayChoiceController = displayChoiceLoader.getController();
        explainChoiceController = explainChoiceLoader.getController();
        mainGameScreenController = mainGameScreenLoader.getController();
        saveScreenController = saveScreenLoader.getController();
    }
    
    public void updateTime(String aTime)
    {
        mainGameScreenController.setTime(aTime);
    }
    
    public void displayMainMenuGUI(MainGameCode aThis) {
        if(!currentStage.isShowing()) currentStage.show();
        mainMenuController.setMainGameCode(aThis);
        currentStage.setScene(mainMenu);

    }

    public void displayMainGameScreen(MainGameCode aThis, Map mapData)
    {
        mainGameScreenController.setMainGameCode(aThis);
        mainGameScreenController.setMapData(mapData);
        currentStage.setScene(mainGameScreen);
    }
    
    public void displayPauseMenuGui(MainGameCode aThis)
    {
        pauseController.setMainGameCode(aThis);
        currentStage.setScene(pauseMenu);
    }
    

    
    
    void displaySocietyChoiceSelectionGUI(MainGameCode aThis) {
        socialValuesSelectionController.reset(); //Resets choices in case of a new game
        socialValuesSelectionController.setMainGameCode(aThis);
        currentStage.setScene(displayChoice);
    }

    public void displayLoadGameDisplay(MainGameCode aThis) {
        currentStage.setScene(loadScreen);
    }

    public void displayInGameChoiceOption(MainGameCode aThis) {
        currentStage.setScene(mainMenu);
    }

    public void explainInGameChoiceProblems() {
        currentStage.setScene(explainChoice);
       
    }

    void displaySaveGameGUI(MainGameCode aThis, TribalCampObject playersCamp) {
        saveScreenController.setMainGameCode(aThis);
        saveScreenController.setSaveData(playersCamp);
        currentStage.setScene(saveScreen);
    }

   
   


}
