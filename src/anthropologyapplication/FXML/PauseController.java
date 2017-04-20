/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.MainGameCode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class PauseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    MainGameCode myMain;
    
    public void setMainGameCode(MainGameCode aThis) {
        myMain = aThis;
    }

    @FXML
    private void ReturnToMainMenuPressed(ActionEvent event) {
        myMain.returningToMainMenu();
    }

    @FXML
    private void SaveGamePressed(ActionEvent event) {
        myMain.SaveGameHasBeenPressed();
    }

    @FXML
    private void LoadGamePressed(ActionEvent event) {
        myMain.loadGameHasBeenPressed();
    }

    @FXML
    private void ContinueGame(ActionEvent event) {
        myMain.continueGamePressed();
    }
}
