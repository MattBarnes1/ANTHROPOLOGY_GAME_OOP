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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class SocialValuesSelectionController implements Initializable {
        LinkedList<SocialChoice> myChoices = new LinkedList<SocialChoice>();// = new SocialChoice("SCENARIOTEXT", "Matrilineal", "Patrilineal",SocialValues.Matrilineal, SocialValues.Patrilineal);
   
            

    
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
        myChoices.add(new SocialChoice("SCENARIOTEXT", "Matrilineal", "Patrilineal","Matrilineal society, also called matriliny, group adhering to a kinship system in which ancestral descent is traced through maternal instead of paternal lines (the l.","Patrilineal society, also called patriliny, group adhering to a kinship system in which ancestral descent is traced through paternal instead of maternal lines.", SocialValues.Matrilineal, SocialValues.Patrilineal));
        myChoices.add(new SocialChoice("SCENARIOTEXT", "Exogamy", "Endogamy","MatrilinialDefinition","PatrilinealDefinition",SocialValues.Exogamy, SocialValues.Endogamy));
        myChoices.add(new SocialChoice("SCENARIOTEXT", "Monogamy", "Polygamy","MatrilinialDefinition","PatrilinealDefinition",SocialValues.Monogamy, SocialValues.Polygamy));
        myChoices.add(new SocialChoice("SCENARIOTEXT", "Matriarchal", "Patriarchal","MatrilinialDefinition","PatrilinealDefinition",SocialValues.Matriarchal, SocialValues.Patriarchal));
        myChoices.add(new SocialChoice("SCENARIOTEXT", "Horticultural", "Pastoral","MatrilinialDefinition","PatrilinealDefinition",SocialValues.Horticultural, SocialValues.Pastoral));
        currentChoiceIterator = myChoices.iterator();
        currentChoice = currentChoiceIterator.next();
        updateQuestionFields();
    }    
    Iterator<SocialChoice> currentChoiceIterator;
    public void nextQuestion()
    {
        currentChoice = currentChoiceIterator.next();
        if(Choice1.isSelected())
        {
            myValues.add(currentChoice.getChoiceSocialValue(0));
        } else if (Choice2.isSelected()) {
            myValues.add(currentChoice.getChoiceSocialValue(1));
        }
    }
    
    
    MainGameCode myMain;
    
    public void setMainGameCode(MainGameCode aThis) {
       
        myMain = aThis;
    }

    public void reset() {
        currentChoiceIterator = myChoices.iterator();
        currentChoice = currentChoiceIterator.next();
        myValues.clear();
        updateQuestionFields();
    }

    @FXML
    private void onNextClicked(ActionEvent event) throws Throwable {
        if(Choice1.isSelected() || Choice2.isSelected())
        {
            if(currentChoiceIterator.hasNext())
            {
                nextQuestion();
                updateQuestionFields();
            } else {
                myMain.createSocietyValues(myValues);
                reset();
            }
        }
    }

    @FXML
    private void onCheckBox1Clicked(ActionEvent event) {
        Choice2.selectedProperty().set(false);
        Choice1.selectedProperty().set(true);
    }

    @FXML
    private void onCheckBox2Clicked(ActionEvent event) {
        Choice1.selectedProperty().set(false);
        Choice2.selectedProperty().set(true);
    }

    private void updateQuestionFields() {
        ScenarioDisplay.setText(currentChoice.getScenarioString());
        Choice1.selectedProperty().set(false);
        Choice2.selectedProperty().set(false);
        Choice1.setText(currentChoice.getChoiceString(0));
        Choice2.setText(currentChoice.getChoiceString(1));
    }

    @FXML
    private void CheckBox1HoveredOn(MouseEvent event) {
        ScenarioDisplay.setText(currentChoice.getDefinition(0));
    }

    @FXML
    private void CheckBox2HoveredOn(MouseEvent event) {
        ScenarioDisplay.setText(currentChoice.getDefinition(1));
    }
    
}
