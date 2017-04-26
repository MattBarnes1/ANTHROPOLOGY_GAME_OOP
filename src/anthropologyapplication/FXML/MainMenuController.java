/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.MainGameCode;
import anthropologyapplication.Time;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javax.naming.OperationNotSupportedException;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class MainMenuController implements Initializable {

    @FXML
    private Pane BackgroundPane;
    @FXML
    private Button NewGameButton;
    @FXML
    private Button LoadGameButton;

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
    
    public void setTime(String aTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void NewGameClick(ActionEvent event) throws OperationNotSupportedException {
        try {
            myMain.newGame();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void LoadGameClicked(ActionEvent event) {
        myMain.loadGame();
    }

    
    
}
