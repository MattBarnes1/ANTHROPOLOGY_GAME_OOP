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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class ExplainChoiceController implements Initializable {

    @FXML
    private VBox DecisionCheckBoxVBox;
    @FXML
    private TextArea ChoiceOutcomeText;
    @FXML
    private Button FinishedReadingButton;

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
    private void FinishedReadingButtonClick(ActionEvent event) {
    }

}
