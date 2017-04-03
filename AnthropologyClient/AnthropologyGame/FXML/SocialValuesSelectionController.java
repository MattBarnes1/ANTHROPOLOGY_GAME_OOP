/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.MainGameCode;
import anthropologyapplication.SocialValues;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class SocialValuesSelectionController implements Initializable {
    SocialChoice myChoices;
    
    SocialChoice currentChoice;
    int currentQuestion = 0;
    ArrayList<SocialValues> myValues = new ArrayList<>();
    
    @FXML
    private TextArea ScenarioDisplay;
    @FXML
    private Button NextButton;
    @FXML
    private CheckBox Choice1;
    @FXML
    private CheckBox Choice2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentChoice = myChoices;
        updateQuestionFields();
    }    

    public void nextQuestion()
    {
        if(Choice1.isSelected())
        {
            myValues.add(currentChoice.getChoiceSocialValue(1));
        } else if (Choice2.isSelected()) {
            myValues.add(currentChoice.getChoiceSocialValue(2));
        }
        currentChoice = currentChoice.getNext();
        if(currentChoice == null)
        {
            myMain.createSocietyValues(myValues);
        }
        //Add social value to myValues
    }
    
    
    MainGameCode myMain;
    
    public void setMainGameCode(MainGameCode aThis) {
        myMain = aThis;
    }

    public void reset() {
        currentChoice = myChoices;
        myValues.clear();
        updateQuestionFields();
    }

    @FXML
    private void onNextClicked(ActionEvent event) {
        if(Choice1.isSelected() || Choice2.isSelected())
        {
            nextQuestion();
            updateQuestionFields();
        }
    }

    @FXML
    private void onCheckBox1Clicked(ActionEvent event) {
        Choice2.selectedProperty().set(false);        
    }

    @FXML
    private void onCheckBox2Clicked(ActionEvent event) {
        Choice1.selectedProperty().set(false);
    }

    private void updateQuestionFields() {
        ScenarioDisplay.setText(currentChoice.getScenarioString());
        Choice1.setText(currentChoice.getChoiceString(1));
        Choice2.setText(currentChoice.getChoiceString(2));
    }
    
}
